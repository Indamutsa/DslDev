/*
 * generated by Xtext 2.24.0
 */
package org.example.expression.ui.contentassist

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor
import org.example.expression.expression.Expression
import org.example.expression.validation.ExpressionsModelUtil

/**
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist
 * on how to customize the content assistant.
 */
class ExpressionProposalProvider extends AbstractExpressionProposalProvider {
	@Inject extension ExpressionsModelUtil

	override completeAtomic_Variable(EObject elem, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		if (elem instanceof Expression)
			elem.variablesDefinedBefore.forEach[
				variable |
				acceptor.accept(
					createCompletionProposal(
						variable.name, variable.name + " - Variable", null, context
					)
				)
			]
	}
}