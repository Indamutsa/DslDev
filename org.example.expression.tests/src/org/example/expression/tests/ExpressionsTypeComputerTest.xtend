package org.example.expression.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.example.expression.expression.Expression
import org.example.expression.expression.ExpressionsModel
import org.junit.Test
import org.junit.runner.RunWith


import static extension org.junit.Assert.*
import org.example.expression.typing.ExpressionType
import org.example.expression.typing.ExpressionTypeComputer


import static org.example.expression.typing.ExpressionTypeComputer.*


@RunWith(XtextRunner)
@InjectWith(ExpressionInjectorProvider)
class ExpressionsTypeComputerTest {

	@Inject extension ParseHelper<ExpressionsModel>
	@Inject extension ExpressionTypeComputer

	@Test def void intConstant() { "10".assertEvalType(INT_TYPE) }
	@Test def void stringConstant() { "'foo'".assertEvalType(STRING_TYPE) }
	@Test def void boolConstant() { "true".assertEvalType(BOOL_TYPE) }

	@Test def void notExp() { "!true".assertEvalType(BOOL_TYPE) }

	@Test def void multiExp() { "1 * 2".assertEvalType(INT_TYPE) }
	@Test def void divExp() { "1 / 2".assertEvalType(INT_TYPE) }

	@Test def void minusExp() { "1 - 2".assertEvalType(INT_TYPE) }

	@Test def void comparisonExp() { "1 < 2".assertEvalType(BOOL_TYPE) }
	@Test def void equalityExp() { "1 == 2".assertEvalType(BOOL_TYPE) }
	@Test def void andExp() { "true && false".assertEvalType(BOOL_TYPE) }
	@Test def void orExp() { "true || false".assertEvalType(BOOL_TYPE) }

	@Test def void numericPlus() { "1 + 2".assertEvalType(INT_TYPE) }
	@Test def void stringPlus() { "'a' + 'b'".assertEvalType(STRING_TYPE) }
	@Test def void numAndStringPlus() { "'a' + 2".assertEvalType(STRING_TYPE) }
	@Test def void numAndStringPlus2() { "2 + 'a'".assertEvalType(STRING_TYPE) }
	@Test def void boolAndStringPlus() { "'a' + true".assertEvalType(STRING_TYPE) }
	@Test def void boolAndStringPlus2() { "false + 'a'".assertEvalType(STRING_TYPE) }

	@Test def void incompletePlusRight() { "1 + ".assertEvalType(INT_TYPE) }

	@Test def void varWithExpression() { "var i = 0".assertType(INT_TYPE) }

	@Test def void varRef() { "var i = 0 eval i".assertType(INT_TYPE) }
	@Test def void varRefToVarDefinedAfter() { "var i = j var j = i".assertType(null) }

	@Test def void testIsInt() { 
		(ExpressionTypeComputer.INT_TYPE).isIntType.assertTrue
	}

	@Test def void testIsString() { 
		(ExpressionTypeComputer.STRING_TYPE).isStringType.assertTrue
	}

	@Test def void testIsBool() { 
		(ExpressionTypeComputer.BOOL_TYPE).isBoolType.assertTrue
	}

	@Test def void testNotIsInt() { 
		(ExpressionTypeComputer.STRING_TYPE).isIntType.assertFalse
	}

	@Test def void testNotIsString() { 
		(ExpressionTypeComputer.INT_TYPE).isStringType.assertFalse
	}

	@Test def void testNotIsBool() { 
		(ExpressionTypeComputer.INT_TYPE).isBoolType.assertFalse
	}

	def assertEvalType(CharSequence input, ExpressionType expectedType) {
		("eval " + input).assertType(expectedType)
	}

	def assertType(CharSequence input, ExpressionType expectedType) {
		input.parse.elements.last.
			expression.assertType(expectedType)
	}

	def assertType(Expression e, ExpressionType expectedType) {
		expectedType.assertSame(e.typeFor)
	}

}