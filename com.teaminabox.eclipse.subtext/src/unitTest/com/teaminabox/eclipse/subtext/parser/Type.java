package com.teaminabox.eclipse.subtext.parser;
import java.io.InputStream;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ICompletionRequestor;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IInitializer;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.IWorkingCopy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.WorkingCopyOwner;

public final class Type implements IType {
	private String fullyQualifiedName;
	private TypeHeirarchy heirarchy;
	
	public Type(String fullyQualifiedName) {
		this(fullyQualifiedName, null);
	}
	public Type(String fullyQualifiedName, IType supertype) {
	    this.fullyQualifiedName = fullyQualifiedName;
		heirarchy = new TypeHeirarchy(supertype);
	}
	public void codeComplete(char[] snippet, int insertion, int position, char[][] localVariableTypeNames, char[][] localVariableNames, int[] localVariableModifiers, boolean isStatic, ICompletionRequestor requestor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public void codeComplete(char[] snippet, int insertion, int position, char[][] localVariableTypeNames, char[][] localVariableNames, int[] localVariableModifiers, boolean isStatic, ICompletionRequestor requestor, WorkingCopyOwner owner) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IField createField(String contents, IJavaElement sibling, boolean force, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IInitializer createInitializer(String contents, IJavaElement sibling, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IMethod createMethod(String contents, IJavaElement sibling, boolean force, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IType createType(String contents, IJavaElement sibling, boolean force, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IMethod[] findMethods(IMethod method) {
		throw new UnsupportedOperationException();
	}
	public String getElementName() {
		throw new UnsupportedOperationException();
	}
	public IField getField(String name) {
		throw new UnsupportedOperationException();
	}
	public IField[] getFields() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}
	public String getFullyQualifiedName(char enclosingTypeSeparator) {
		throw new UnsupportedOperationException();
	}
	public IInitializer getInitializer(int occurrenceCount) {
		throw new UnsupportedOperationException();
	}
	public IInitializer[] getInitializers() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IMethod getMethod(String name, String[] parameterTypeSignatures) {
		throw new UnsupportedOperationException();
	}
	public IMethod[] getMethods() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IPackageFragment getPackageFragment() {
		throw new UnsupportedOperationException();
	}
	public String getSuperclassName() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public String[] getSuperInterfaceNames() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IType getType(String name) {
		throw new UnsupportedOperationException();
	}
	public String getTypeQualifiedName() {
		throw new UnsupportedOperationException();
	}
	public String getTypeQualifiedName(char enclosingTypeSeparator) {
		throw new UnsupportedOperationException();
	}
	public IType[] getTypes() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public boolean isAnonymous() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public boolean isClass() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public boolean isInterface() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public boolean isLocal() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public boolean isMember() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy loadTypeHierachy(InputStream input, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy newSupertypeHierarchy(IProgressMonitor monitor) {
        return heirarchy;
	}
	public ITypeHierarchy newSupertypeHierarchy(ICompilationUnit[] workingCopies, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy newSupertypeHierarchy(IWorkingCopy[] workingCopies, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy newSupertypeHierarchy(WorkingCopyOwner owner, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy newTypeHierarchy(IJavaProject project, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy newTypeHierarchy(IJavaProject project, WorkingCopyOwner owner, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy newTypeHierarchy(IProgressMonitor monitor) throws JavaModelException {
		return heirarchy;
	}
	public ITypeHierarchy newTypeHierarchy(ICompilationUnit[] workingCopies, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy newTypeHierarchy(IWorkingCopy[] workingCopies, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public ITypeHierarchy newTypeHierarchy(WorkingCopyOwner owner, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public String[][] resolveType(String typeName) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public String[][] resolveType(String typeName, WorkingCopyOwner owner) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public IClassFile getClassFile() {
		throw new UnsupportedOperationException();
	}
	public ICompilationUnit getCompilationUnit() {
		throw new UnsupportedOperationException();
	}
	public IType getDeclaringType() {
		throw new UnsupportedOperationException();
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
	public void copy(IJavaElement container, IJavaElement sibling, String rename, boolean replace, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public void delete(boolean force, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public void move(IJavaElement container, IJavaElement sibling, String rename, boolean replace, IProgressMonitor monitor) throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public void rename(String name, boolean replace, IProgressMonitor monitor) throws JavaModelException {
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
	public String getSuperclassTypeSignature() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public String[] getSuperInterfaceTypeSignatures() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public String[] getTypeParameterSignatures() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public boolean isAnnotation() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
	public boolean isEnum() throws JavaModelException {
		throw new UnsupportedOperationException();
	}
}
