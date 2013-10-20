package com.teaminabox.eclipse.subtext.parser;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public final class Method implements IMethod {
    private IType type;
    private String name;
    
    public Method(IType type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getElementName() {
        return name;
    }

    public String[] getExceptionTypes() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public int getNumberOfParameters() {
        return 0;
    }

    public String[] getParameterNames() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public String[] getParameterTypes() {
        return new String[] {};
    }

    public String getReturnType() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public String getSignature() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public boolean isConstructor() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public boolean isMainMethod() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public boolean isSimilar(IMethod method) {
        throw new UnsupportedOperationException();
    }

    public IClassFile getClassFile() {
        throw new UnsupportedOperationException();
    }

    public ICompilationUnit getCompilationUnit() {
         throw new UnsupportedOperationException();
    }

    public IType getDeclaringType() {
        return type;
    }

    public int getFlags() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public ISourceRange getNameRange() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public IType getType(String name, int occurrenceCount) {
        throw new UnsupportedOperationException();
    }

    public boolean isBinary() {
        throw new UnsupportedOperationException();
    }

    public boolean exists() {
        throw new UnsupportedOperationException();
    }

    public IJavaElement getAncestor(int ancestorType) {
        throw new UnsupportedOperationException();
    }

    public IResource getCorrespondingResource() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public int getElementType() {
        throw new UnsupportedOperationException();
    }

    public String getHandleIdentifier() {
        throw new UnsupportedOperationException();
    }

    public IJavaModel getJavaModel() {
        throw new UnsupportedOperationException();
    }

    public IJavaProject getJavaProject() {
        throw new UnsupportedOperationException();
    }

    public IOpenable getOpenable() {
        throw new UnsupportedOperationException();
    }

    public IJavaElement getParent() {
        throw new UnsupportedOperationException();
    }

    public IPath getPath() {
        throw new UnsupportedOperationException();
    }

    public IJavaElement getPrimaryElement() {
        throw new UnsupportedOperationException();
    }

    public IResource getResource() {
        throw new UnsupportedOperationException();
    }

    public ISchedulingRule getSchedulingRule() {
        throw new UnsupportedOperationException();
    }

    public IResource getUnderlyingResource() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public boolean isReadOnly() {
        throw new UnsupportedOperationException();
    }

    public boolean isStructureKnown() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public String getSource() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public ISourceRange getSourceRange() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public void copy(IJavaElement container, IJavaElement sibling,
            String rename, boolean replace, IProgressMonitor monitor)
            throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public void delete(boolean force, IProgressMonitor monitor)
            throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public void move(IJavaElement container, IJavaElement sibling,
            String rename, boolean replace, IProgressMonitor monitor)
            throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public void rename(String name, boolean replace, IProgressMonitor monitor)
            throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public IJavaElement[] getChildren() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public boolean hasChildren() throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public Object getAdapter(Class adapter) {
        throw new UnsupportedOperationException();
    }

	public String[] getTypeParameterSignatures() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
}
