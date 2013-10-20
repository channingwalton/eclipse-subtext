package com.teaminabox.eclipse.subtext.model;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class TypePattern implements PointCut {
	private String regex;
	private int arrayCount;
	
	public TypePattern(String regex) {
		this.regex = regex;
	}
	
	public void setArrayCount(int count) {
		arrayCount = count;
	}
	
	protected String getRegex() {
	    return regex;
	}
	
	public boolean matches(IType type) throws JavaModelException {
		return matches(type, 0);
	}
	
	public boolean matches(IType type, int arrayCount) throws JavaModelException {
		return matches(type.getFullyQualifiedName(), arrayCount);
	}
	
	protected boolean matches(String fullyQualifiedTypeName, int arrayCount) {
		return fullyQualifiedTypeName.matches(regex) && this.arrayCount == arrayCount;
	}
	
	public boolean matches(IMethod method) throws JavaModelException {
	    return false;
	}
	
    public boolean isMethodPointCut() {
        return false;
    }
    
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getPatternName()).append("(").append(regex);
		for (int i = 0 ; i < arrayCount; i++) {
			sb.append("[]");
		}
		sb.append(")");
		return sb.toString();
	}
	
	protected String getPatternName() {
		return "TYPE";
	}
}
