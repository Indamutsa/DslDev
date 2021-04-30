package org.example.expression.validation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.example.expression.expression.AbstractElement;
import org.example.expression.expression.Expression;
import org.example.expression.expression.ExpressionsModel;
import org.example.expression.expression.Variable;
import org.example.expression.expression.VariableRef;

@SuppressWarnings("all")
public class ExpressionsModelUtil {
  /**
   * Caching usually introduces a few problems since we must avoid that its contents
   * become stale. Xtext provides a cache that relieves us from worrying about this
   * problem, org.eclipse.xtext.util.IResourceScopeCache . This cache is
   * automatically cleared when a resource changes, thus its contents are never stale.
   * Moreover, its default implementation is annotated as com.google.inject.
   * Singleton , thus all our DSL components will share the same instance of the cache.
   */
  @Inject
  private IResourceScopeCache cache;
  
  public boolean isVariableDefinedBefore(final VariableRef varRef) {
    return this.variablesDefinedBefore(varRef).contains(varRef.getVariable());
  }
  
  public Set<Variable> variablesDefinedBefore(final Expression e) {
    return this.variablesDefinedBefore(EcoreUtil2.<AbstractElement>getContainerOfType(e, AbstractElement.class));
  }
  
  /**
   * We specify the AbstractElement as the key, its resource and a lambda for the
   * Provider parameter. The lambda is simply the original implementation of the
   * method body. Remember that the lambda will be called only in case of a cache miss.
   * This is all we have to do to use the cache.
   */
  public Set<Variable> variablesDefinedBefore(final AbstractElement containingElement) {
    final Provider<Set<Variable>> _function = () -> {
      Set<Variable> _xblockexpression = null;
      {
        EObject _eContainer = containingElement.eContainer();
        final EList<AbstractElement> allElements = ((ExpressionsModel) _eContainer).getElements();
        _xblockexpression = IterableExtensions.<Variable>toSet(EcoreUtil2.<Variable>typeSelect(allElements.subList(
          0, 
          allElements.indexOf(containingElement)), Variable.class));
      }
      return _xblockexpression;
    };
    return this.cache.<Set<Variable>>get(containingElement, containingElement.eResource(), _function);
  }
}
