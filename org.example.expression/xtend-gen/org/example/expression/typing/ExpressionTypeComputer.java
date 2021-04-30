package org.example.expression.typing;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.Arrays;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pair;
import org.example.expression.expression.And;
import org.example.expression.expression.BoolConstant;
import org.example.expression.expression.Comparison;
import org.example.expression.expression.Equality;
import org.example.expression.expression.Expression;
import org.example.expression.expression.IntConstant;
import org.example.expression.expression.Minus;
import org.example.expression.expression.MulOrDiv;
import org.example.expression.expression.Not;
import org.example.expression.expression.Or;
import org.example.expression.expression.Plus;
import org.example.expression.expression.StringConstant;
import org.example.expression.expression.Variable;
import org.example.expression.expression.VariableRef;
import org.example.expression.typing.BoolType;
import org.example.expression.typing.ExpressionType;
import org.example.expression.typing.IntType;
import org.example.expression.typing.StringType;
import org.example.expression.validation.ExpressionsModelUtil;

@SuppressWarnings("all")
public class ExpressionTypeComputer {
  public static final StringType STRING_TYPE = new StringType();
  
  public static final IntType INT_TYPE = new IntType();
  
  public static final BoolType BOOL_TYPE = new BoolType();
  
  @Inject
  @Extension
  private ExpressionsModelUtil _expressionsModelUtil;
  
  @Inject
  private IResourceScopeCache cache;
  
  /**
   * Using singletons will
   * allow us to simply compare a computed type with such static instances (remember
   * that triple equal in Xtend, === , corresponds to Java object reference equality)
   */
  public boolean isStringType(final ExpressionType type) {
    return (type == ExpressionTypeComputer.STRING_TYPE);
  }
  
  public boolean isIntType(final ExpressionType type) {
    return (type == ExpressionTypeComputer.INT_TYPE);
  }
  
  public boolean isBoolType(final ExpressionType type) {
    return (type == ExpressionTypeComputer.BOOL_TYPE);
  }
  
  /**
   * We now write a method, typeFor , which, given an Expression , returns an
   * ExpressionsType object. We use the dispatch methods for special cases and switch
   * for simple cases. For expressions whose type can be computed directly, we write
   */
  protected ExpressionType _typeFor(final Expression e) {
    ExpressionType _switchResult = null;
    boolean _matched = false;
    if (e instanceof StringConstant) {
      _matched=true;
      _switchResult = ExpressionTypeComputer.STRING_TYPE;
    }
    if (!_matched) {
      if (e instanceof IntConstant) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.INT_TYPE;
      }
    }
    if (!_matched) {
      if (e instanceof BoolConstant) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.BOOL_TYPE;
      }
    }
    if (!_matched) {
      if (e instanceof Not) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.BOOL_TYPE;
      }
    }
    if (!_matched) {
      if (e instanceof MulOrDiv) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.INT_TYPE;
      }
    }
    if (!_matched) {
      if (e instanceof Minus) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.INT_TYPE;
      }
    }
    if (!_matched) {
      if (e instanceof Comparison) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.BOOL_TYPE;
      }
    }
    if (!_matched) {
      if (e instanceof Equality) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.BOOL_TYPE;
      }
    }
    if (!_matched) {
      if (e instanceof And) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.BOOL_TYPE;
      }
    }
    if (!_matched) {
      if (e instanceof Or) {
        _matched=true;
        _switchResult = ExpressionTypeComputer.BOOL_TYPE;
      }
    }
    return _switchResult;
  }
  
  protected ExpressionType _typeFor(final Plus e) {
    ExpressionType _xblockexpression = null;
    {
      final ExpressionType leftType = this.typeFor(e.getLeft());
      Expression _right = e.getRight();
      ExpressionType _typeFor = null;
      if (_right!=null) {
        _typeFor=this.typeFor(_right);
      }
      final ExpressionType rightType = _typeFor;
      ExpressionType _xifexpression = null;
      if ((this.isStringType(leftType) || this.isStringType(rightType))) {
        _xifexpression = ExpressionTypeComputer.STRING_TYPE;
      } else {
        _xifexpression = ExpressionTypeComputer.INT_TYPE;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected ExpressionType _typeFor(final VariableRef varRef) {
    boolean _isVariableDefinedBefore = this._expressionsModelUtil.isVariableDefinedBefore(varRef);
    boolean _not = (!_isVariableDefinedBefore);
    if (_not) {
      return null;
    } else {
      final Variable variable = varRef.getVariable();
      Pair<String, Variable> _mappedTo = Pair.<String, Variable>of("type", variable);
      final Provider<ExpressionType> _function = () -> {
        return this.typeFor(variable.getExpression());
      };
      return this.cache.<ExpressionType>get(_mappedTo, variable.eResource(), _function);
    }
  }
  
  public ExpressionType typeFor(final Expression e) {
    if (e instanceof Plus) {
      return _typeFor((Plus)e);
    } else if (e instanceof VariableRef) {
      return _typeFor((VariableRef)e);
    } else if (e != null) {
      return _typeFor(e);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(e).toString());
    }
  }
}
