/*
 * generated by Xtext 2.24.0
 */
package org.example.expression.ui

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider
import org.example.expression.ui.hover.ExpressionEObjectHoverProvider

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@FinalFieldsConstructor
class ExpressionUiModule extends AbstractExpressionUiModule {
	def Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return ExpressionEObjectHoverProvider
	}
}