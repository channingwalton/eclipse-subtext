package com.teaminabox.eclipse.subtext.parser;

import java.io.OutputStream;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.ITypeHierarchyChangedListener;
import org.eclipse.jdt.core.JavaModelException;

public final class TypeHeirarchy implements ITypeHierarchy {
    IType supertype;
    
    public TypeHeirarchy(IType supertype) {
        this.supertype = supertype;
    }

    public void addTypeHierarchyChangedListener(ITypeHierarchyChangedListener listener) {
        throw new UnsupportedOperationException();
    }

    public boolean contains(IType type) {
        throw new UnsupportedOperationException();
    }

    public boolean exists() {
        throw new UnsupportedOperationException();
    }

    public IType[] getAllClasses() {
        throw new UnsupportedOperationException();
    }

    public IType[] getAllInterfaces() {
        throw new UnsupportedOperationException();
    }

    public IType[] getAllSubtypes(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getAllSuperclasses(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getAllSuperInterfaces(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getAllSupertypes(IType type) {
        throw new UnsupportedOperationException();
     }

    public IType[] getAllTypes() {
        if (supertype == null) {
            return new IType[] {new Type("java.lang.Object")};
        }
        
        return new IType[] {supertype, new Type("java.lang.Object")};
     }

    public int getCachedFlags(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getExtendingInterfaces(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getImplementingClasses(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getRootClasses() {
        throw new UnsupportedOperationException();
    }

    public IType[] getRootInterfaces() {
        throw new UnsupportedOperationException();
    }

    public IType[] getSubclasses(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getSubtypes(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType getSuperclass(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getSuperInterfaces(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType[] getSupertypes(IType type) {
        throw new UnsupportedOperationException();
    }

    public IType getType() {
        throw new UnsupportedOperationException();
    }

    public void refresh(IProgressMonitor monitor) throws JavaModelException {
        throw new UnsupportedOperationException();
    }

    public void removeTypeHierarchyChangedListener(ITypeHierarchyChangedListener listener) {
        throw new UnsupportedOperationException();
    }

    public void store(OutputStream outputStream, IProgressMonitor monitor) throws JavaModelException {
        throw new UnsupportedOperationException();
    }
}
