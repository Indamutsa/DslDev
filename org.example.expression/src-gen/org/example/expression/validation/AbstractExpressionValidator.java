/*
 * generated by Xtext 2.24.0
 */
package org.example.expression.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;

public abstract class AbstractExpressionValidator extends AbstractDeclarativeValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>();
		result.add(org.example.expression.expression.ExpressionPackage.eINSTANCE);
		return result;
	}
}