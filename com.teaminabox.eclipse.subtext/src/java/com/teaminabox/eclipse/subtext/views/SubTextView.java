package com.teaminabox.eclipse.subtext.views;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.ILocationProvider;
import org.eclipse.ui.part.ViewPart;

import com.teaminabox.eclipse.subtext.SubTextConstants;
import com.teaminabox.eclipse.subtext.SubTextPlugin;
import com.teaminabox.eclipse.subtext.model.SubTextDoc;

public final class SubTextView extends ViewPart {
	private static final String HEADER = "style/header.html";
	private static final String FOOTER = "style/footer.html";

	private Browser browser;
	private IResourceChangeListener resourceChangeListener;
	private ISelectionListener selectionListener;
	private ICompilationUnit compilationUnit;
	private IPartListener2 partListener;
	private Action action1;

	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NULL);
		browser.setText("");
		addSelectionListener();
		addPartListener();
		addSubTextListener();
		makeActions();
		contributeToActionBars();
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				SubTextView.this.reprocess();
			}
		};
		action1.setText("SubText");
		action1.setToolTipText("Return to SubText");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));

	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		getSite().getPage().removeSelectionListener(selectionListener);
		getSite().getPage().removePartListener(partListener);
	}

	private void addPartListener() {
		partListener = new IPartListener2() {
			public void partActivated(IWorkbenchPartReference ref) {
				SubTextView.this.handlePartEvent(ref);
			}

			public void partOpened(IWorkbenchPartReference ref) {
				SubTextView.this.handlePartEvent(ref);
			}

			public void partVisible(IWorkbenchPartReference ref) {
				SubTextView.this.handlePartEvent(ref);
			}

			public void partInputChanged(IWorkbenchPartReference ref) {
				SubTextView.this.handlePartEvent(ref);
			}

			public void partBroughtToTop(IWorkbenchPartReference ref) {
			}

			public void partClosed(IWorkbenchPartReference ref) {
			}

			public void partDeactivated(IWorkbenchPartReference ref) {
			}

			public void partHidden(IWorkbenchPartReference ref) {
			}
		};
		getSite().getPage().addPartListener(partListener);
	}

	private void addSelectionListener() {
		selectionListener = new ISelectionListener() {
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				SubTextView.this.handle(selection);
			}
		};
		getSite().getPage().addSelectionListener(selectionListener);
	}

	private void handle(ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		IStructuredSelection structured = (IStructuredSelection) selection;

		Object object = structured.getFirstElement();
		if (object instanceof IFile) {
			process((IFile) object);
		} else if (object instanceof ICompilationUnit) {
			process((ICompilationUnit) object, false);
		} else if (object instanceof IJavaElement) {
			process((IJavaElement) object);
		}
	}

	private void addSubTextListener() {
		resourceChangeListener = new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				SubTextView.this.processResourceChange(event);
			}
		};
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
	}

	private void processResourceChange(IResourceChangeEvent event) {
		try {
			IResourceDelta rootDelta = event.getDelta();
			rootDelta.accept(new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) throws CoreException {
					return processResourceChangeDelta(delta);
				}
			});
		} catch (Exception e) {
			SubTextPlugin.log(e);
		}
	}

	private boolean processResourceChangeDelta(IResourceDelta delta) {
		if (delta.getKind() != IResourceDelta.CHANGED && delta.getKind() != IResourceDelta.REMOVED && delta.getKind() != IResourceDelta.ADDED) {
			return true;
		}
		IResource resource = delta.getResource();
		if (resource.getType() == IResource.FILE && SubTextConstants.SUBTEXT_EXTENSION.equalsIgnoreCase(resource.getFileExtension())) {
			SubTextView.this.reprocess();
			return false;
		}
		return true;
	}

	private void handlePartEvent(IWorkbenchPartReference ref) {
		IEditorPart editor = ref.getPage().getActiveEditor();
		if (editor == null) {
			return;
		}
		IEditorInput input = editor.getEditorInput();
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileEditorInput = (IFileEditorInput) input;
			process(fileEditorInput.getFile());
		} else if (input instanceof ILocationProvider) {
			ILocationProvider provider = (ILocationProvider) input;
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(provider.getPath(input));
			if (resource instanceof IFile) {
				process((IFile) resource);
			}
		}
	}

	private void process(IJavaElement element) {
		ICompilationUnit compilationUnit = (ICompilationUnit) element.getAncestor(IJavaElement.COMPILATION_UNIT);
		if (compilationUnit != null) {
			process(compilationUnit, false);
		}
	}

	private void process(IFile file) {
		String extension = file.getFileExtension();
		if (extension != null && extension.equals(SubTextConstants.JAVA_EXTENSION)) {
			process((ICompilationUnit) JavaCore.create(file), false);
		}
	}

	private void process(ICompilationUnit compilationUnit, boolean force) {
		if (!force && this.compilationUnit != null && this.compilationUnit.equals(compilationUnit)) {
			return;
		}
		this.compilationUnit = compilationUnit;
		final StringBuffer buffer = new StringBuffer(1024);
		buffer.append(getFragment(SubTextView.HEADER));
		SubTextPlugin.getDefault().forEachSubTextDoc(compilationUnit, new SubTextDoc.Closure() {
			public void execute(SubTextDoc subTextDoc) {
				buffer.append("<h3>");
				buffer.append(subTextDoc.getName());
				buffer.append("</h3>");
				boolean html = subTextDoc.getDocumentation().toLowerCase().startsWith("<div");
				if (!html) {
					buffer.append("<p>");
				}
				buffer.append(subTextDoc.getDocumentation());
				if (!html) {
					buffer.append("</p>");
				}
			}
		});
		buffer.append(getFragment(SubTextView.FOOTER));
		browser.setText(buffer.toString());
	}

	private String getFragment(String fragment) {
		try {
			IPath path = new Path(fragment);
			InputStream stream = SubTextPlugin.getDefault().openStream(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			int c;
			while ((c = bufferedReader.read()) != -1) {
				buffer.append((char) c);
			}
			return buffer.toString();
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
	}

	private void reprocess() {
		if (compilationUnit != null) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					// compilationUnit may have been made null here
					if (compilationUnit != null && compilationUnit.exists()) {
						process(compilationUnit, true);
					} else {
						compilationUnit = null;
						browser.setText("");
					}
				}
			});
		}
	}

	public void setFocus() {
		browser.setFocus();
	}
}