package org.example.expression.tests;

import com.google.inject.Inject;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.example.expression.expression.AbstractElement;
import org.example.expression.expression.ExpressionsModel;
import org.example.expression.interpreter.ExpressionInterpreter;
import org.example.expression.tests.ExpressionInjectorProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(ExpressionInjectorProvider.class)
@SuppressWarnings("all")
public class ExpressionsInterpreterTest {
  @Inject
  @Extension
  private ParseHelper<ExpressionsModel> _parseHelper;
  
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  @Inject
  @Extension
  private ExpressionInterpreter _expressionInterpreter;
  
  /**
   * Note that in order to correctly test the interpreter, we check that there are no errors
   * in the input (since that is the assumption of the interpreter itself) and we compare the
   * actual objects, not their string representation. This way, we are sure that the object
   * returned by the interpreter is of the expected Java type.
   */
  @Test
  public void intConstant() {
    this.assertInterpret("eval 1", Integer.valueOf(1));
  }
  
  @Test
  public void boolConstant() {
    this.assertInterpret("eval true", Boolean.valueOf(true));
  }
  
  @Test
  public void stringConstant() {
    this.assertInterpret("eval \'abc\'", "abc");
  }
  
  public ExpressionsModel assertInterpret(final CharSequence input, final Object expected) {
    try {
      ExpressionsModel _parse = this._parseHelper.parse(input);
      final Procedure1<ExpressionsModel> _function = (ExpressionsModel it) -> {
        this._validationTestHelper.assertNoErrors(it);
        Assert.assertEquals(expected, this._expressionInterpreter.interpret(IterableExtensions.<AbstractElement>last(it.getElements()).getExpression()));
      };
      return ObjectExtensions.<ExpressionsModel>operator_doubleArrow(_parse, _function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
