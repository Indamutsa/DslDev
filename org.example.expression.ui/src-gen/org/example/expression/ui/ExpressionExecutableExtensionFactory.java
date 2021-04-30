/*
 * generated by Xtext 2.24.0
 */
package org.example.expression.ui;

import com.google.inject.Injector;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.example.expression.ui.internal.ExpressionActivator;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class ExpressionExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(ExpressionActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		ExpressionActivator activator = ExpressionActivator.getInstance();
		return activator != null ? activator.getInjector(ExpressionActivator.ORG_EXAMPLE_EXPRESSION_EXPRESSION) : null;
	}

}