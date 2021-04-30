package org.example.expression.typing

import com.google.inject.Inject
import org.eclipse.xtext.util.IResourceScopeCache
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
import org.example.expression.validation.ExpressionsModelUtil

class ExpressionTypeComputer {
	public static val STRING_TYPE = new StringType
	public static val INT_TYPE = new IntType
	public static val BOOL_TYPE = new BoolType

	@Inject extension ExpressionsModelUtil

	@Inject IResourceScopeCache cache

	/**
	 * Using singletons will
	 * allow us to simply compare a computed type with such static instances (remember
	 * that triple equal in Xtend, === , corresponds to Java object reference equality)
	 */
	def isStringType(ExpressionType type) {
		type === STRING_TYPE
	}

	def isIntType(ExpressionType type) {
		type === INT_TYPE
	}

	def isBoolType(ExpressionType type) {
		type === BOOL_TYPE
	}

	/**
	 * We now write a method, typeFor , which, given an Expression , returns an
	 * ExpressionsType object. We use the dispatch methods for special cases and switch
	 * for simple cases. For expressions whose type can be computed directly, we write
	 */
	def dispatch ExpressionType typeFor(Expression e) {
		switch (e) {
			StringConstant: STRING_TYPE
			IntConstant: INT_TYPE
			BoolConstant: BOOL_TYPE
			Not: BOOL_TYPE
			MulOrDiv: INT_TYPE
			Minus: INT_TYPE
			Comparison: BOOL_TYPE
			Equality: BOOL_TYPE
			And: BOOL_TYPE
			Or: BOOL_TYPE
		}
	}

	def dispatch ExpressionType typeFor(Plus e) {
		val leftType = e.left.typeFor
		val rightType = e.right?.typeFor
		if (leftType.isStringType || rightType.isStringType)
			STRING_TYPE
		else
			INT_TYPE
	}

	def dispatch ExpressionType typeFor(VariableRef varRef) {
		if (!varRef.isVariableDefinedBefore)
			return null
		else {
			val variable = varRef.variable
			// use a pair as the key, not to conflict with the
			// use of cache we make in ExpressionsModelUtil
			return cache.get("type" -> variable, variable.eResource) [
				variable.expression.typeFor
			]
		}
	}
}
