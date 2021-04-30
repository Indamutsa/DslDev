package org.example.expression.validation

import org.example.expression.expression.AbstractElement
import org.example.expression.expression.Expression
import org.example.expression.expression.ExpressionsModel
import org.example.expression.expression.Variable

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.example.expression.expression.VariableRef
import com.google.inject.Inject
import org.eclipse.xtext.util.IResourceScopeCache

class ExpressionsModelUtil {
	/**
	 * Caching usually introduces a few problems since we must avoid that its contents
	 * become stale. Xtext provides a cache that relieves us from worrying about this
	 * problem, org.eclipse.xtext.util.IResourceScopeCache . This cache is
	 * automatically cleared when a resource changes, thus its contents are never stale.
	 * Moreover, its default implementation is annotated as com.google.inject.
	 * Singleton , thus all our DSL components will share the same instance of the cache.
	 */
	@Inject IResourceScopeCache cache

	def isVariableDefinedBefore(VariableRef varRef) {
		varRef.variablesDefinedBefore.contains(varRef.variable)
	}

	def variablesDefinedBefore(Expression e) {
		e.getContainerOfType(AbstractElement).variablesDefinedBefore
	}

	/**
	 * We must provide the key of the cache, which can be any object, the Resource
	 * associated with the cache, and the Provider whose get() method is called
	 * automatically if no value is associated to the specified key.
	 * Let's use this cache in the ExpressionsModelUtil for the implementation of
	 * variablesDefinedBefore 
	 */
	/**
	 * We specify the AbstractElement as the key, its resource and a lambda for the
	 * Provider parameter. The lambda is simply the original implementation of the
	 * method body. Remember that the lambda will be called only in case of a cache miss.
	 * This is all we have to do to use the cache.
	 */
	def variablesDefinedBefore(AbstractElement containingElement) {
		cache.get(containingElement, containingElement.eResource) [
			val allElements = (containingElement.eContainer as ExpressionsModel).elements

			allElements.subList(
				0,
				allElements.indexOf(containingElement)
			).typeSelect(Variable).toSet
		]
	}
}
