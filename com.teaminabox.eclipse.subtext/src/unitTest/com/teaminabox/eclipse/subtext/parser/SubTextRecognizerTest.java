package com.teaminabox.eclipse.subtext.parser;

import java.io.StringReader;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import junit.framework.Assert;
import junit.framework.TestCase;
import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.teaminabox.eclipse.subtext.model.PointCut;

public final class SubTextRecognizerTest extends TestCase {
	public SubTextRecognizerTest(String name) {
		super(name);
	}
	
	public void testSimpleTypePatternInDefaultPackage() throws Exception {
		PointCut cut = parse("Bob");
		checkMatch(cut, new Type("Bob"));
		checkNoMatch(cut, new Type("Bobble"));
		checkNoMatch(cut, new Type("bilBob"));
		checkNoMatch(cut, new Type("com.Bob"));
		checkNoMatch(cut, new Type("Bob.com"));
	}
	
	public void testSimpleTypePatternInNonDefaultPackage() throws Exception {
		PointCut cut = parse("com.Bob");
		checkMatch(cut, new Type("com.Bob"));
		checkNoMatch(cut, new Type("Bob"));
		checkNoMatch(cut, new Type("com.Bobble"));
		checkNoMatch(cut, new Type("a.com.Bob"));
		checkNoMatch(cut, new Type("com.Bob.a"));
	}

	public void testTypePatternWithStarInDefaultPackage() throws Exception {
		PointCut cut = parse("B*b");
		checkMatch(cut, new Type("Bb"));
		checkMatch(cut, new Type("Bob"));
		checkMatch(cut, new Type("Blob"));
		checkNoMatch(cut, new Type("Bobble"));
		checkNoMatch(cut, new Type("bilBob"));
		checkNoMatch(cut, new Type("com.Bob"));
		checkNoMatch(cut, new Type("Bob.com"));
		checkNoMatch(cut, new Type("Bl.ob"));
	}
	
	public void testTypePatternWithStarInNonDefaultPackage() throws Exception {
		PointCut cut = parse("com.B*b");
		checkMatch(cut, new Type("com.Bb"));
		checkMatch(cut, new Type("com.Bob"));
		checkMatch(cut, new Type("com.Blob"));
		checkNoMatch(cut, new Type("com.Bobble"));
		checkNoMatch(cut, new Type("com.bilBob"));
		checkNoMatch(cut, new Type("Bob"));
		checkNoMatch(cut, new Type("Bob.com"));
		checkNoMatch(cut, new Type("com.Bl.ob"));
	}

	public void testTypePatternWithDoubleDotAtBeginning() throws Exception {
		PointCut cut = parse("..Bob");
		checkMatch(cut, new Type("Bob"));
		checkMatch(cut, new Type("com.Bob"));
		checkMatch(cut, new Type("com.bill.Bob"));
		checkNoMatch(cut, new Type("Bobble"));
	}
	
	public void testTypePatternWithDoubleDotInMiddle() throws Exception {
		PointCut cut = parse("com..Bob");
		checkMatch(cut, new Type("com.Bob"));
		checkMatch(cut, new Type("com.bill.Bob"));
		checkMatch(cut, new Type("com.bill.ben.Bob"));
		checkNoMatch(cut, new Type("Bob"));
		checkNoMatch(cut, new Type("comBob"));
		checkNoMatch(cut, new Type("com.Bob.bill"));
		checkNoMatch(cut, new Type("com.Bobs"));
	}
	
	public void testTypePatternWithMultiplePackageElements() throws Exception {
		PointCut cut = parse("com.bob..b*l.B*t");
		checkMatch(cut, new Type("com.bob.bill.Bert"));
		checkMatch(cut, new Type("com.bob.util.bill.Bert"));
		checkMatch(cut, new Type("com.bob.util.moreutils.bill.Bert"));
		checkNoMatch(cut, new Type("bob.bill.Bert"));
		checkNoMatch(cut, new Type("com.bob.bert.Bert"));
		checkNoMatch(cut, new Type("com.bob.bill.Bill"));
	}

	public void testSubtypePattern() throws Exception {
		PointCut cut = parse("Bob+");
		checkMatch(cut, new Type("Bob"));
		checkMatch(cut, new Type("Bill", new Type("Bob")));
		checkNoMatch(cut, new Type("com.Bob"));
		checkNoMatch(cut, new Type("Bill"));
		checkNoMatch(cut, new Type("Bobble"));
	}
	
	public void testSubtypePatternWithOtherWildcards() throws Exception {
		PointCut cut = parse("com..*.*Bob+");
		checkMatch(cut, new Type("com.bill.Bob"));
		checkMatch(cut, new Type("com.bert.bill.Bob"));
		checkMatch(cut, new Type("com.bert.bill.SpongeBob"));
		checkMatch(cut, new Type("Sdhet", new Type("com.bill.Bob")));
		checkMatch(cut, new Type("Sdhet", new Type("com.bert.bill.Bob")));
		checkMatch(cut, new Type("Sdhet", new Type("com.bert.bill.SpongeBob")));
		checkNoMatch(cut, new Type("Bob"));
		checkNoMatch(cut, new Type("Bert", new Type("com.Bob")));
	}
	
	public void testNoArgMethodPattern() throws Exception {
	    PointCut cut = parse("Bob.do()");
	    checkMatch(cut, new Method(new Type("Bob"), "do"));
	    checkNoMatch(cut, new Method(new Type("Bob"), "done"));
	    checkNoMatch(cut, new Method(new Type("Bobble"), "do"));
	    checkNoMatch(cut, new Method(new Type("com.Bob"), "do"));
	}
	
	private PointCut parse(String expression) throws RecognitionException, TokenStreamException {
		SubTextRecognizer parser = new SubTextRecognizer(new SubTextLexer(new StringReader(expression)));
		parser.pointCut();
		SubTextTreeWalker treeWalker = new SubTextTreeWalker();
		return treeWalker.pointcut(parser.getAST());
	}
	
	private void checkMatch(PointCut cut, IType type) throws JavaModelException {
		Assert.assertTrue(type.getFullyQualifiedName(), cut.matches(type));
	}
	
	private void checkNoMatch(PointCut cut, IType type) throws JavaModelException {
		Assert.assertFalse(type.getFullyQualifiedName(), cut.matches(type));
	}
	
	private void checkMatch(PointCut cut, IMethod method) throws JavaModelException {
		Assert.assertTrue(method.getElementName(), cut.matches(method));
	}
	
	private void checkNoMatch(PointCut cut, IMethod method) throws JavaModelException {
		Assert.assertFalse(method.getElementName(), cut.matches(method));
	}
}
