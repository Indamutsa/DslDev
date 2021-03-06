package org.example.expression.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.example.expression.expression.And
import org.example.expression.expression.BoolConstant
import org.example.expression.expression.Comparison
import org.example.expression.expression.Equality
import org.example.expression.expression.Expression
import org.example.expression.expression.ExpressionsModel
import org.example.expression.expression.IntConstant
import org.example.expression.expression.Minus
import org.example.expression.expression.MulOrDiv
import org.example.expression.expression.Not
import org.example.expression.expression.Or
import org.example.expression.expression.Plus
import org.example.expression.expression.StringConstant
import org.example.expression.expression.VariableRef
import org.junit.Test
import org.junit.runner.RunWith

import static extension org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(ExpressionInjectorProvider)
class ExpressionParsingTest {

	@Inject extension ParseHelper<ExpressionsModel>

	@Test def void testEvalIntConstant() {
		"eval 10".parse.assertNotNull
	}

	@Test def void testEvalStringConstant() {
		'eval "a string"'.parse.assertNotNull
	}

	@Test def void testEvalBoolConstant() {
		"eval true".parse.assertNotNull
	}

	/**
	 *  Testing both the variable declararation and initialization
	 *  and eval of the expression (variable)
	 */
	@Test def void testVariable() {
		"var i = 10".parse.assertNotNull
	}

	@Test def void testVariableReference() {
		'''
			var i = 10
			eval i
		'''.parse => [
			(elements.last.expression as VariableRef).variable.assertSame(elements.head)
		]
	}

	/**
	 * We will use this method in the rest of the section to test the associativity of 
	 * expression. For instance, for testing the associativity of an addition, we write:
	 */
	@Test def void testPlus() {
		"10 + 5 + 1 + 2".assertRepr("(((10 + 5) + 1) + 2)")
	}

	/**
	 * Note that the rule for parentheses does not perform any assignment to features; thus,
	 * given the parsed text (exp) the AST will contain a node for exp , not for (exp) . This
	 * can be verified by this test
	 */
	@Test def void testParenthesis() {
		10.assertEquals(("eval (10)".parse.elements.head.expression as IntConstant).value)
	}

	@Test def void testPlusWithParenthesis() {
		"( 10 + 5 ) + ( 1 + 2 )".assertRepr("((10 + 5) + (1 + 2))")
	}

	@Test
	def void testMinus() {
		"10 + 5 - 1 - 2".assertRepr("(((10 + 5) - 1) - 2)")
	}

	@Test
	def void testMulOrDiv() {
		"10 * 5 / 1 * 2".assertRepr("(((10 * 5) / 1) * 2)")
	}

	@Test
	def void testPlusMulPrecedence() {
		"10 + 5 * 2 - 5 / 1".assertRepr("((10 + (5 * 2)) - (5 / 1))")
	}

	@Test def void testComparison() {
		"10 <= 5 < 2 > 5".assertRepr("(((10 <= 5) < 2) > 5)")
	}

	@Test def void testEqualityAndComparison() {
		"true == 5 <= 2".assertRepr("(true == (5 <= 2))")
	}

	@Test def void testAndOr() {
		"true || false && 1 < 0".assertRepr("(true || (false && (1 < 0)))")
	}

	@Test def void testNot() {
		"!true||false".assertRepr("((!true) || false)")
	}

	@Test def void testNotWithParentheses() {
		"!(true||false)".assertRepr("(!(true || false))")
	}

	@Test def void testPrecedences() {
		"!true||false&&1>(1/3+5*2)".assertRepr("((!true) || (false && (1 > ((1 / 3) + (5 * 2)))))")
	}

	def private assertRepr(CharSequence input, CharSequence expected) {
		("eval " + input).parse => [
			expected.assertEquals(
				elements.last.expression.stringRepr
			)
		]
	}

	def private String stringRepr(Expression e) {
		switch (e) {
			Plus: '''(??e.left.stringRepr?? + ??e.right.stringRepr??)'''
			Minus: '''(??e.left.stringRepr?? - ??e.right.stringRepr??)'''
			MulOrDiv: '''(??e.left.stringRepr?? ??e.op?? ??e.right.stringRepr??)'''
			Comparison: '''(??e.left.stringRepr?? ??e.op?? ??e.right.stringRepr??)'''
			Equality: '''(??e.left.stringRepr?? ??e.op?? ??e.right.stringRepr??)'''
			And: '''(??e.left.stringRepr?? && ??e.right.stringRepr??)'''
			Or: '''(??e.left.stringRepr?? || ??e.right.stringRepr??)'''
			Not: '''(!??e.expression.stringRepr??)'''
			IntConstant: '''??e.value??'''
			StringConstant: '''??e.value??'''
			BoolConstant: '''??e.value??'''
			VariableRef: '''??e.variable.name??'''
		}.toString
	}

}
