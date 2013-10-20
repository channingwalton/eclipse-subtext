package com.teaminabox.eclipse.subtext.model;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public final class SubtypePattern extends TypePattern {
	public SubtypePattern(String regex) {
		super(regex);
	}
	
	public boolean matches(IType type, int arrayCount) throws JavaModelException {
		if (super.matches(type, arrayCount)) {
		    return true;
		}
		
		IType[] supertypes = type.newSupertypeHierarchy(new NullProgressMonitor()).getAllTypes();
		for (int i = 0 ; i < supertypes.length ; i++) {
			if (super.matches(supertypes[i].getFullyQualifiedName(), arrayCount)) {
				return true;
			}
		}
		
		return false;
	}
	
	protected String getPatternName() {
		return "SUBTYPE";
	}
}
