/*
 * generated by Xtext 2.24.0
 */
package org.example.expression.validation

import com.google.inject.Inject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.validation.Check
import org.example.expression.expression.And
import org.example.expression.expression.Comparison
import org.example.expression.expression.Equality
import org.example.expression.expression.Expression
import org.example.expression.expression.ExpressionPackage
import org.example.expression.expression.Minus
import org.example.expression.expression.MulOrDiv
import org.example.expression.expression.Not
import org.example.expression.expression.Or
import org.example.expression.expression.Plus
import org.example.expression.expression.VariableRef
import org.example.expression.typing.ExpressionType
import org.example.expression.typing.ExpressionTypeComputer

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class ExpressionValidator extends AbstractExpressionValidator {

	protected static val ISSUE_CODE_PREFIX = "org.example.expression."
	public static val FORWARD_REFERENCE = ISSUE_CODE_PREFIX + "ForwardReference"
	public static val TYPE_MISMATCH = ISSUE_CODE_PREFIX + "TypeMismatch"

	@Inject extension ExpressionsModelUtil
	@Inject extension ExpressionTypeComputer

	/**
	 * For Not , And , and Or , we check that the sub-expressions have type boolean, and
	 * we pass the EMF features corresponding to the sub-expression. (The case for Or
	 * is similar to the case of And and it is therefore not shown).
	 * Following the same approach, it is easy to check that the sub-expressions of Minus
	 * and MultiOrDiv both have integer types (we leave this as an exercise, but you can
	 * look at the sources of the example).
	 * For an Equality expression, we must check that the two sub-expressions have the
	 * same type. This holds true also for a Comparison expression, but in this case, we also
	 * check that the sub-expressions do not have type boolean, since in our DSL, we do not
	 * want to compare two boolean values. The implementation of these @Check methods
	 * are as follows, using two additional reusable methods
	 */
	@Check
	def void checkForwardReference(VariableRef varRef) {
		val variable = varRef.getVariable()
		if (!varRef.isVariableDefinedBefore)
			error("variable forward reference not allowed: '" + variable.name + "'",
				ExpressionPackage.eINSTANCE.variableRef_Variable, FORWARD_REFERENCE, variable.name)
	}

	@Check def checkType(Not not) {
		checkExpectedBoolean(not.expression, ExpressionPackage.Literals.NOT__EXPRESSION)
	}

	@Check def checkType(And and) {
		checkExpectedBoolean(and.left, ExpressionPackage.Literals.AND__LEFT)
		checkExpectedBoolean(and.right, ExpressionPackage.Literals.AND__RIGHT)
	}

	@Check
	def checkType(Or or) {
		checkExpectedBoolean(or.left, ExpressionPackage.Literals.OR__LEFT)
		checkExpectedBoolean(or.right, ExpressionPackage.Literals.OR__RIGHT)
	}

	@Check
	def checkType(MulOrDiv mulOrDiv) {
		checkExpectedInt(mulOrDiv.left, ExpressionPackage.Literals.MUL_OR_DIV__LEFT)
		checkExpectedInt(mulOrDiv.right, ExpressionPackage.Literals.MUL_OR_DIV__RIGHT)
	}

	@Check
	def checkType(Minus minus) {
		checkExpectedInt(minus.left, ExpressionPackage.Literals.MINUS__LEFT)
		checkExpectedInt(minus.right, ExpressionPackage.Literals.MINUS__RIGHT)
	}

	@Check def checkType(Equality equality) {
		val leftType = getTypeAndCheckNotNull(equality.left, ExpressionPackage.Literals.EQUALITY__LEFT)
		val rightType = getTypeAndCheckNotNull(equality.right, ExpressionPackage.Literals.EQUALITY__RIGHT)
		checkExpectedSame(leftType, rightType)
	}

	@Check def checkType(Comparison comparison) {
		val leftType = getTypeAndCheckNotNull(comparison.left, ExpressionPackage.Literals.COMPARISON__LEFT)
		val rightType = getTypeAndCheckNotNull(comparison.right, ExpressionPackage.Literals.COMPARISON__RIGHT)
		checkExpectedSame(leftType, rightType)
		checkNotBoolean(leftType, ExpressionPackage.Literals.COMPARISON__LEFT)
		checkNotBoolean(rightType, ExpressionPackage.Literals.COMPARISON__RIGHT)
	}

	/**
	 * The final check concerns the Plus expression; according to our type system, if one
	 * of the two sub-expressions has type string, everything is fine and therefore all
	 * these combinations are accepted as valid: string+string, int+int, string+boolean,
	 * and string+int (and the corresponding specular cases). We cannot add two boolean
	 * expressions or an integer and a boolean. Therefore, when one of the two
	 * sub-expressions has type integer or when they both have a type different from
	 * string, we must check that they do not have type boolean
	 */
	@Check def checkType(Plus plus) {
		val leftType = getTypeAndCheckNotNull(plus.left, ExpressionPackage.Literals.PLUS__LEFT)
		val rightType = getTypeAndCheckNotNull(plus.right, ExpressionPackage.Literals.PLUS__RIGHT)
		if (leftType.isIntType || rightType.isIntType || (!leftType.isStringType && !rightType.isStringType)) {
			checkNotBoolean(leftType, ExpressionPackage.Literals.PLUS__LEFT)
			checkNotBoolean(rightType, ExpressionPackage.Literals.PLUS__RIGHT)
		}
	}

	def private checkExpectedSame(ExpressionType left, ExpressionType right) {
		if (right !== null && left !== null && right != left) {
			error("expected the same type, but was " + left + ", " + right,
				ExpressionPackage.Literals.EQUALITY.getEIDAttribute(), TYPE_MISMATCH)
		}
	}

	def private checkNotBoolean(ExpressionType type, EReference reference) {
		if (type.isBoolType) {
			error("cannot be boolean", reference, TYPE_MISMATCH)
		}
	}

	def private checkExpectedBoolean(Expression exp, EReference reference) {
		checkExpectedType(exp, ExpressionTypeComputer.BOOL_TYPE, reference)
	}

	def private checkExpectedInt(Expression exp, EReference reference) {
		checkExpectedType(exp, ExpressionTypeComputer.INT_TYPE, reference)
	}

	def private checkExpectedType(Expression exp, ExpressionType expectedType, EReference reference) {
		val actualType = getTypeAndCheckNotNull(exp, reference)
		if (actualType != expectedType)
			error(
				"expected " + expectedType + " type, but was " + actualType,
				reference,
				TYPE_MISMATCH
			)
	}

	def private ExpressionType getTypeAndCheckNotNull(Expression exp, EReference reference) {
		val type = exp?.typeFor
		if (type === null)
			error("null type", reference, TYPE_MISMATCH)
		return type;
	}
}