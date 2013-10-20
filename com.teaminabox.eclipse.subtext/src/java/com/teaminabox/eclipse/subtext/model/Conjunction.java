package com.teaminabox.eclipse.subtext.model;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public final class Conjunction implements PointCut {
	private PointCut lhs;
	private PointCut rhs;
	
	public Conjunction(PointCut lhs, PointCut rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	public boolean matches(IType type) throws JavaModelException {
		return lhs.matches(type) && rhs.matches(type);
	}
	
	public boolean matches(IMethod method) throws JavaModelException {
		return (lhs.matches(method.getDeclaringType()) || lhs.matches(method)) && rhs.matches(method)
			|| lhs.matches(method) && (rhs.matches(method.getDeclaringType()) || rhs.matches(method));
	}
	
    public boolean isMethodPointCut() {
        return lhs.isMethodPointCut() || rhs.isMethodPointCut();
    }
	
	public String toString() {
	    return "AND(" + lhs + ", " + rhs + ")";
	}
}
