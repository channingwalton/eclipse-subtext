package com.teaminabox.eclipse.subtext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.teaminabox.eclipse.subtext.model.PointCut;
import com.teaminabox.eclipse.subtext.model.SubTextDoc;
import com.teaminabox.eclipse.subtext.model.SubTextDoc.Closure;
import com.teaminabox.eclipse.subtext.parser.SubTextLexer;
import com.teaminabox.eclipse.subtext.parser.SubTextRecognizer;
import com.teaminabox.eclipse.subtext.parser.SubTextTreeWalker;

public class SubTextPlugin extends AbstractUIPlugin {

	private static SubTextPlugin plugin;
	private ResourceBundle resourceBundle;

	public SubTextPlugin(IPluginDescriptor descriptor) {
		super(descriptor);
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle(SubTextConstants.PLUGIN_RESOURCES);
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	public static SubTextPlugin getDefault() {
		return plugin;
	}

	public static String getResourceString(String key) {
		ResourceBundle bundle = SubTextPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void forEachSubTextDoc(ICompilationUnit element, Closure closure) {
		try {
			forEachSubTextDocProtected(element, closure);
		} catch (CoreException e) {
			SubTextPlugin.log(e);
		}
	}

	private void forEachSubTextDocProtected(final ICompilationUnit element, final Closure closure) throws CoreException {
		element.getResource().getProject().accept(new IResourceVisitor() {
			public boolean visit(IResource resource) {
				if (SubTextPlugin.this.isSubTextDocument(resource)) {
					IFile file = (IFile) resource;
					SubTextPlugin.this.handleSubTextDoc(element, closure, file);
				}
				return true;
			}

		}, IResource.DEPTH_INFINITE, IResource.FILE);		
	}

	private boolean isSubTextDocument(IResource resource) {
		return resource instanceof IFile && resource.exists() && SubTextConstants.SUBTEXT_EXTENSION.equalsIgnoreCase(resource.getFileExtension());
	}

	private void handleSubTextDoc(ICompilationUnit element, Closure closure, IFile file) {
		try {
			SubTextDoc subtext = createSubTextDoc(loadAll(file).trim());
			if (subtext != null) {
			    handleSubTextDoc(element, closure, subtext);
			}
		} catch (Exception e) {
			SubTextPlugin.log(e);
		}
	}

	private void handleSubTextDoc(ICompilationUnit element, Closure closure, SubTextDoc subtext) throws JavaModelException {
        IType[] types = element.getAllTypes();
        for (int i = 0; i < types.length; i++) {
            if (handleSubTextDocForType(types[i], closure, subtext)) {
                return;
            }
        }
    }
	
	private boolean handleSubTextDocForType(IType type, Closure closure, SubTextDoc subtext) throws JavaModelException {
    	if (subtext.getPointCut().matches(type)) {
    		closure.execute(subtext);
    		return true;
    	} else if (subtext.getPointCut().isMethodPointCut()) {
    	    return handleSubTextDocForMethods(type, closure, subtext);
    	}
    	return false;
    }
	
	private boolean handleSubTextDocForMethods(IType type, Closure closure, SubTextDoc subtext) throws JavaModelException {
	    IMethod[] methods = type.getMethods();
	    for (int i = 0 ; i < methods.length ; i++) {
	        if (subtext.getPointCut().matches(methods[i])) {
	            closure.execute(subtext);
	            return true;
	        }
	    }
	    return false;
	}

    private SubTextDoc createSubTextDoc(String subtext) throws RecognitionException, TokenStreamException {
		int semiIndex = subtext.indexOf(';');
		if (semiIndex == -1) {
			return null;
		}
		
		String[] declarationParts = splitDeclaration(subtext.substring(0, semiIndex));
		if (declarationParts == null) {
			return null;
		}
		
		String documentation = subtext.substring(semiIndex + 1, subtext.length()).trim();
		return new SubTextDoc(declarationParts[0], parsePointCut(declarationParts[1]), documentation);
	}
	
	private String[] splitDeclaration(String declaration) {
		if (!declaration.startsWith(SubTextConstants.SUBTEXT_DECLARATION)) {
			return null;
		}
		
		int nameStart = SubTextConstants.SUBTEXT_DECLARATION.length();
		if (nameStart == declaration.length() || !Character.isWhitespace(declaration.charAt(nameStart))) {
			return null;
		}
		do {
			nameStart++;
		} while (nameStart < declaration.length() && Character.isWhitespace(declaration.charAt(nameStart)));
		if (nameStart == declaration.length() || !Character.isJavaIdentifierStart(declaration.charAt(nameStart))) {
			return null;
		}
		
		int nameEnd = nameStart + 1;
		while (nameEnd < declaration.length() && Character.isJavaIdentifierPart(declaration.charAt(nameEnd))) {
			nameEnd++;
		}
		if (nameEnd >= declaration.length() || !Character.isWhitespace(declaration.charAt(nameEnd))) {
			return null;
		}
		
		String expression = declaration.substring(nameEnd).trim();
		if (expression.length() == 0) {
			return null;
		}
		return new String[] {declaration.substring(nameStart, nameEnd), expression};
	}

	private PointCut parsePointCut(String expression) throws RecognitionException, TokenStreamException {
		SubTextRecognizer parser = new SubTextRecognizer(new SubTextLexer(new StringReader(expression)));
		parser.pointCut();
		SubTextTreeWalker treeWalker = new SubTextTreeWalker();
		return treeWalker.pointcut(parser.getAST());
	}

	private String loadAll(IFile file) throws IOException, CoreException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.getContents()));
		StringWriter stringWriter = new StringWriter(1000);
		PrintWriter writer = new PrintWriter(stringWriter);
		String line;
		while ((line = reader.readLine()) != null) {
			writer.println(line);
		}
		return stringWriter.getBuffer().toString();
	}

	public static void log(String message) {
		getDefault().getLog().log(new Status(IStatus.OK, SubTextConstants.PLUGIN_ID, IStatus.OK, message, null));
	}

	public static void log(Throwable ex) {
		if (ex instanceof CoreException) {
			getDefault().getLog().log(((CoreException) ex).getStatus());
		} else {
			getDefault().getLog().log(new Status(IStatus.ERROR, SubTextConstants.PLUGIN_ID, IStatus.OK, "Caught exception", ex));
		}
	}
}
