package com.teaminabox.eclipse.subtext.model;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public interface PointCut {
	boolean matches(IType type) throws JavaModelException;
	boolean matches(IMethod method) throws JavaModelException;
	boolean isMethodPointCut();
}
