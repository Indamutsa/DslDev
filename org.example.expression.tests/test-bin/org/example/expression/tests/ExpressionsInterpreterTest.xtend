package org.example.expression.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.example.expression.expression.ExpressionsModel
import org.example.expression.interpreter.ExpressionInterpreter
import org.example.expression.tests.ExpressionInjectorProvider
import org.junit.Test
import org.junit.runner.RunWith

import static extension org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(ExpressionInjectorProvider)
class ExpressionsInterpreterTest {
	@Inject extension ParseHelper<ExpressionsModel>
	@Inject extension ValidationTestHelper
	@Inject extension ExpressionInterpreter

	/**
	 * Note that in order to correctly test the interpreter, we check that there are no errors
	 * in the input (since that is the assumption of the interpreter itself) and we compare the
	 * actual objects, not their string representation. This way, we are sure that the object
	 * returned by the interpreter is of the expected Java type.
	 */
	@Test def void intConstant() { "eval 1".assertInterpret(1) }

	@Test def void boolConstant() { "eval true".assertInterpret(true) }

	@Test def void stringConstant() { "eval 'abc'".assertInterpret("abc") }

	def assertInterpret(CharSequence input, Object expected) {
		input.parse => [
			assertNoErrors
			expected.assertEquals(elements.last.expression.interpret)
		]
	}
}
