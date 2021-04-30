package org.example.expression.ui.hover

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.Diagnostician
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider
import org.example.expression.expression.Expression
import org.example.expression.interpreter.ExpressionInterpreter
import org.example.expression.typing.ExpressionTypeComputer

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

/**
 * We can provide a custom implementation of text hovering (that is, the pop-
 * up window that comes up when we hover for some time on a specific editor region)
 * so that it shows the type of the expression and its evaluation. We refer to the Xtext
 * documentation for the details of the customization of text hovering; here, we only show
 * our implementation (note that we create a multiline string using HTML syntax):
 */
/**
 * Remember that our interpreter is based on the assumption that it is invoked only on
 * an EMF model that contains no error. We invoke our validator programmatically
 * using the EMF API that is, the Diagnostician class.
 */
class ExpressionEObjectHoverProvider extends DefaultEObjectHoverProvider {
	@Inject extension ExpressionTypeComputer
	@Inject extension ExpressionInterpreter

	override getHoverInfoAsHtml(EObject o) {
		if (o instanceof Expression && o.programHasNoError) {
			val exp = o as Expression
			return '''
				<p>
				type  : <b>«exp.typeFor.toString»</b> <br>
				value : <b>«exp.interpret.toString»</b>
				</p>
			'''
		} else
			return super.getHoverInfoAsHtml(o)
	}

	def programHasNoError(EObject o) {
		Diagnostician.INSTANCE.validate(o.rootContainer).children.empty
	}

}
