package org.example.expression.interpreter

import com.google.inject.Inject
import org.eclipse.xtext.util.IResourceScopeCache
import org.example.expression.expression.AbstractElement
import org.example.expression.expression.And
import org.example.expression.expression.BoolConstant
import org.example.expression.expression.Comparison
import org.example.expression.expression.Equality
import org.example.expression.expression.Expression
import org.example.expression.expression.IntConstant
import org.example.expression.expression.Minus
import org.example.expression.expression.MulOrDiv
import org.example.expression.expression.Not
import org.example.expression.expression.Or
import org.example.expression.expression.Plus
import org.example.expression.expression.StringConstant
import org.example.expression.expression.VariableRef
import org.example.expression.typing.ExpressionTypeComputer

class ExpressionInterpreter {
	@Inject extension ExpressionTypeComputer
	@Inject IResourceScopeCache cache

	/**
	 * The idea is that this
	 * interpreter, given an AbstractElement , returns a Java object, which represents
	 * the evaluation of that element. Of course, we want the object with the result of the
	 * evaluation to be of the correct Java type; that is, if we evaluate a boolean expression,
	 * the corresponding object should be a Java boolean object.
	 */
	/**
	 * When implementing the interpreter we make the assumption that the passed
	 * AbstractElement is valid. Therefore, we will not check for null sub-expressions.
	 */
	/**
	 * We write the classes for the interpreter in the new Java sub-package
	 * interpreter. If you want to make its classes visible outside the
	 * main plug-in project, for example, for testing, you should add this
	 * package to the list of exported packages in the Runtime tab of the
	 * MANIFEST.MF editor
	 */
	/**
	 * Note that the method interpret returns an Object , and thus we need to cast the
	 * result of the invocation on sub-expressions to the right Java type. We do not perform
	 * an instanceof check because, as hinted previously, the interpreter assumes that the
	 * input is well-typed.
	 */
	def dispatch Object interpret(Expression e) {
// 	IntConstant: e.value
		switch (e) {
			/**
			 * Note that the feature value for an IntConstant object is of Java type int and for
			 * a StringConstant object, it is of Java type String , and thus we do not need any
			 * conversion.
			 */
			IntConstant:
				e.value
			BoolConstant:
				Boolean.parseBoolean(e.value)
			StringConstant:
				e.value
			Not:
				!(e.expression.interpret as Boolean)
			MulOrDiv: {
				val left = e.left.interpret as Integer
				val right = e.right.interpret as Integer

				if (e.op == "*")
					left * right
				else
					left / right
			}
			Minus:
				(e.left.interpret as Integer) - (e.right.interpret as Integer)
			Plus: {
				if (e.left.typeFor.isStringType || e.right.typeFor.isStringType)
					e.left.interpret.toString + e.right.interpret.toString
				else
					(e.left.interpret as Integer) + (e.right.interpret as Integer)
			}
			Equality: {
				if (e.op == '==')
					e.left.interpret == e.right.interpret
				else
					e.left.interpret != e.right.interpret
			}
			And: {
				(e.left.interpret as Boolean ) || (e.right.interpret as Boolean)
			}
			Or: {
				(e.left.interpret as Boolean ) || (e.right.interpret as Boolean)
			}
			Comparison: {
				if (e.left.typeFor.isStringType) {
					val left = e.left.interpret as String
					val right = e.right.interpret as String

					switch (e.op) {
						case '<': left < right
						case '>': left > right
						case '>=': left >= right
						case '<=': left <= right
						default: false
					}
				} else {
					val left = e.left.interpret as Integer
					val right = e.left.interpret as Integer

					switch (e.op) {
						case '<': left < right
						case '>': left > right
						case '>=': left >= right
						case '<=': left <= right
						default: false
					}
				}
			}
			VariableRef: // e.variable.interpret
			{
				/**
				 * Another part that can be optimized is the interpretation of a variable reference in the
				 * ExpressionsInterpreter : instead of interpreting the same variable over and over
				 * again, we can cache the result of the interpretation of variables
				 */
				// avoid interpreting the same variable over and over again
				val v = e.variable
				cache.get("interpret" -> v, e.eResource) [
					v.expression.interpret
				]
			}
		}
	}

	def dispatch Object interpret(AbstractElement e) {
		e.expression.interpret
	}
}
