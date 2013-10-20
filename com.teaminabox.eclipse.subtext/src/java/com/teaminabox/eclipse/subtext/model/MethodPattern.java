package com.teaminabox.eclipse.subtext.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

public final class MethodPattern implements PointCut {
	private TypePattern	definingType;
	private String		nameRegex;
	private List		parameterTypePatterns;
	private boolean		ignoreParameters;

	public MethodPattern(TypePattern definingType, String nameRegex) {
		this.definingType = definingType;
		this.nameRegex = nameRegex;
	}

	public void ignoreParameters() {
		ignoreParameters = true;
	}

	public void addParameter(TypePattern parameter) {
		if (parameterTypePatterns == null) {
			parameterTypePatterns = new ArrayList();
		}
		parameterTypePatterns.add(parameter);
	}

	private int getNumberOfParameters() {
		return parameterTypePatterns == null ? 0 : parameterTypePatterns.size();
	}

	private TypePattern getParameterTypePattern(int index) {
		return (TypePattern) parameterTypePatterns.get(index);
	}

	public boolean matches(IType type) throws JavaModelException {
		return false;
	}

	public boolean matches(IMethod method) throws JavaModelException {
		if (!definingType.matches(method.getDeclaringType()) || !method.getElementName().matches(nameRegex)) {
			return false;
		}

		if (ignoreParameters) { 
			return true;
		}

		if (method.getNumberOfParameters() != getNumberOfParameters()) {
			return false;
		}

		String[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			int arrayIndex;
			String type = getSimpleType(parameterTypes[i]);
			String[][] resolvedType = method.getDeclaringType().resolveType(type);
			if (resolvedType != null || resolvedType.length == 1) {
				IType parameterType = method.getJavaProject().findType(Signature.toQualifiedName(resolvedType[0]));
				int arrayCount = Signature.getArrayCount(parameterTypes[i]);
				if (parameterType == null || !getParameterTypePattern(i).matches(parameterType, arrayCount)) {
					return false;
				}
			}
		}

		return true;
	}

	private String getSimpleType(String string) {
		String type = Signature.toString(string);
		int arrayIndex = type.indexOf('[');
		if (arrayIndex != -1) {
			type = type.substring(0, arrayIndex);
		}
		return type;
	}

	public boolean isMethodPointCut() {
		return true;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("METHOD(").append(definingType).append(", ").append(nameRegex).append('(');
		for (int i = 0 ; i < getNumberOfParameters() ; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(getParameterTypePattern(i));
		}
		sb.append("))");
		return sb.toString();
	}
}