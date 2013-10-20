header {
	package com.teaminabox.eclipse.subtext.parser;
	
	import com.teaminabox.eclipse.subtext.model.PointCut;
	import com.teaminabox.eclipse.subtext.model.Conjunction;
	import com.teaminabox.eclipse.subtext.model.Disjunction;
	import com.teaminabox.eclipse.subtext.model.Inverter;
	import com.teaminabox.eclipse.subtext.model.MethodPattern;
	import com.teaminabox.eclipse.subtext.model.SubtypePattern;
	import com.teaminabox.eclipse.subtext.model.TypePattern;
}

class SubTextTreeWalker extends TreeParser;
options {
	importVocab=SubText;
	defaultErrorHandler=false;	
	k=1;
	buildAST=false;
}

{
	private static final String PACKAGE_WILDCARD_REGEX = "(\\.|^)(.+\\.)*";
	private static final String PACKAGE_SEPARATOR_REGEX = "\\.";
}

pointcut returns [PointCut toReturn]:
		toReturn=typePattern
	|	toReturn=methodPattern
	|	toReturn=logicalTerm
;

protected typePattern returns [TypePattern toReturn]:
	{ String pattern = ""; }
	#(	tp:TYPE_PATTERN
		( PREFIXED_PACKAGE_WILDCARD { pattern += SubTextTreeWalker.PACKAGE_WILDCARD_REGEX; } )?
		{	pattern += tp.getText(); }
		(
			(
					(PACKAGE_WILDCARD { pattern += SubTextTreeWalker.PACKAGE_WILDCARD_REGEX; })
				|	(PACKAGE_SEPARATOR { pattern += SubTextTreeWalker.PACKAGE_SEPARATOR_REGEX; })
			)
			id:IdentifierPattern { pattern += #id.getText(); }
		)*
		(		(SUBTYPES_WILDCARD { toReturn = new SubtypePattern(pattern); })
			|	({ toReturn = new TypePattern(pattern); })
		)
	)
;

protected methodPattern returns [MethodPattern toReturn]:
	{ TypePattern tp; }
	#(METHOD_PATTERN tp=typePattern mp:IdentifierPattern
		{ toReturn = new MethodPattern(tp, #mp.getText()); }
		argumentList[toReturn]
	)
;

protected argumentList[MethodPattern methodPattern]:
		(PARAMETER_LIST_WILDCARD { methodPattern.ignoreParameters(); })
	|	(
			{
				TypePattern parameterType;
				int arrayCount = 0;
			}
			parameterType=typePattern
			(ArrayPattern { arrayCount++; } )*
			{
				parameterType.setArrayCount(arrayCount);
				methodPattern.addParameter(parameterType);
			}
		)*
;

protected logicalTerm returns [PointCut toReturn]:
	{
		PointCut lhs;
		PointCut rhs;
	}
	(		#(NOT rhs=pointcut) { toReturn = new Inverter(rhs); }
		|	#(AND lhs=pointcut rhs=pointcut) { toReturn = new Conjunction(lhs, rhs); }
		|	#(OR lhs=pointcut rhs=pointcut) { toReturn = new Disjunction(lhs, rhs); }
	)
;