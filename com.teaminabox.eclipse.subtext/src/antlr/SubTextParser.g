header {
	package com.teaminabox.eclipse.subtext.parser;
}
	
class SubTextRecognizer extends Parser;
options {
	k=3;                           
	exportVocab=SubText;              
	defaultErrorHandler=false;
	buildAST=true;
}

tokens {
	TYPE_PATTERN;
	METHOD_PATTERN;
	PACKAGE_SEPARATOR;
	PACKAGE_WILDCARD;
	PREFIXED_PACKAGE_WILDCARD;
	PARAMETER_LIST_WILDCARD;
}

pointCut:	
	pointcutExpressionOrTerm
	EOF!
;

pointcutExpressionOrTerm:
	pointcutExpressionAndTerm (OR^ pointcutExpressionAndTerm)*
;

protected pointcutExpressionAndTerm:
	pointcutExpressionNotTerm (AND^ pointcutExpressionNotTerm)*
;

protected pointcutExpressionNotTerm:
	(NOT^)? (pointcutExpressionAtom | pointcutExpressionParenthesisedTerm)
;

protected pointcutExpressionParenthesisedTerm:
	OPEN_PARENTHESIS! pointcutExpressionOrTerm CLOSE_PARENTHESIS!
;

protected pointcutExpressionAtom:
	typePattern (DOT^ methodPattern { #DOT.setType(METHOD_PATTERN); } )?
;

protected typePattern:
	(doubleDotWildcard[PREFIXED_PACKAGE_WILDCARD])?
	(id:IdentifierPattern^ { #id.setType(TYPE_PATTERN); })
	
	(	options { greedy = true; }:
		(packageSeparator | doubleDotWildcard[PACKAGE_WILDCARD])
		IdentifierPattern
	)*
	(SUBTYPES_WILDCARD)?
;

protected methodPattern:
	IdentifierPattern (!OPEN_PARENTHESIS) (parameterList)? (!CLOSE_PARENTHESIS)
;

protected parameterList:
		(parameterPattern)=> (parameterPattern (COMMA! typePattern)*)
	|	doubleDotWildcard[PARAMETER_LIST_WILDCARD]
;

protected parameterPattern:
	typePattern (ArrayPattern)*
;

protected packageSeparator:
	d:DOT { #d.setType(PACKAGE_SEPARATOR); }
;

protected doubleDotWildcard[int tokenType]:
	d:DOT^ (!DOT) { #d.setText(".."); #d.setType(tokenType); }
;


class SubTextLexer extends Lexer;
options {
	exportVocab=SubText;
	k=2;
	charVocabulary='\u0003'..'\uFFFF';
	defaultErrorHandler=false;
}
	
AND:  "&&";
OR: "||";
NOT: '!';
OPEN_PARENTHESIS: '(';
CLOSE_PARENTHESIS: ')';
SUBTYPES_WILDCARD: '+';
DOT: '.';
COMMA : ',';

IdentifierPattern:
	IDENTIFIER_INITIAL_CHARACTERS (IDENTIFIER_SUBSEQUENT_CHARACTERS)*
;

protected IDENTIFIER_INITIAL_CHARACTERS:
	'a'..'z' | 'A'..'Z' | '_' | '$' | CHARACTERS_WILDCARD
;

protected IDENTIFIER_SUBSEQUENT_CHARACTERS:
	IDENTIFIER_INITIAL_CHARACTERS | '0'..'9'
;

protected CHARACTERS_WILDCARD : '*' {
	$setText("[a-zA-Z_$0-9]*");
}
;

ArrayPattern: "[]"
;

WS:
	(	' '
		|	'\t'
		|	'\f'
			// handle newlines
		|	(	options {generateAmbigWarnings=false;}
			:	"\r\n"  // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
			{ newline(); }
		)+
		{ _ttype = Token.SKIP; }
	;