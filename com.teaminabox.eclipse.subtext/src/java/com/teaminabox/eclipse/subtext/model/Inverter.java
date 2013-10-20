package com.teaminabox.eclipse.subtext.model;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public final class Inverter implements PointCut {
	private PointCut toInvert;
	
	public Inverter(PointCut toInvert) {
		this.toInvert = toInvert;
	}
	
	public boolean matches(IType type) throws JavaModelException {
		return !toInvert.matches(type);
	}
	
    public boolean matches(IMethod method) throws JavaModelException {
        return !toInvert.matches(method);
    }
	
    public boolean isMethodPointCut() {
        return toInvert.isMethodPointCut();
    }
    
    public String toString() {
	    return "NOT(" + toInvert + ")";
	}
}
