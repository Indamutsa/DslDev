package org.example.expression.tests;

import com.google.inject.Inject;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.example.expression.expression.AbstractElement;
import org.example.expression.expression.Expression;
import org.example.expression.expression.ExpressionsModel;
import org.example.expression.tests.ExpressionInjectorProvider;
import org.example.expression.typing.ExpressionType;
import org.example.expression.typing.ExpressionTypeComputer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(ExpressionInjectorProvider.class)
@SuppressWarnings("all")
public class ExpressionsTypeComputerTest {
  @Inject
  @Extension
  private ParseHelper<ExpressionsModel> _parseHelper;
  
  @Inject
  @Extension
  private ExpressionTypeComputer _expressionTypeComputer;
  
  @Test
  public void intConstant() {
    this.assertEvalType("10", ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void stringConstant() {
    this.assertEvalType("\'foo\'", ExpressionTypeComputer.STRING_TYPE);
  }
  
  @Test
  public void boolConstant() {
    this.assertEvalType("true", ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void notExp() {
    this.assertEvalType("!true", ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void multiExp() {
    this.assertEvalType("1 * 2", ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void divExp() {
    this.assertEvalType("1 / 2", ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void minusExp() {
    this.assertEvalType("1 - 2", ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void comparisonExp() {
    this.assertEvalType("1 < 2", ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void equalityExp() {
    this.assertEvalType("1 == 2", ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void andExp() {
    this.assertEvalType("true && false", ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void orExp() {
    this.assertEvalType("true || false", ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void numericPlus() {
    this.assertEvalType("1 + 2", ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void stringPlus() {
    this.assertEvalType("\'a\' + \'b\'", ExpressionTypeComputer.STRING_TYPE);
  }
  
  @Test
  public void numAndStringPlus() {
    this.assertEvalType("\'a\' + 2", ExpressionTypeComputer.STRING_TYPE);
  }
  
  @Test
  public void numAndStringPlus2() {
    this.assertEvalType("2 + \'a\'", ExpressionTypeComputer.STRING_TYPE);
  }
  
  @Test
  public void boolAndStringPlus() {
    this.assertEvalType("\'a\' + true", ExpressionTypeComputer.STRING_TYPE);
  }
  
  @Test
  public void boolAndStringPlus2() {
    this.assertEvalType("false + \'a\'", ExpressionTypeComputer.STRING_TYPE);
  }
  
  @Test
  public void incompletePlusRight() {
    this.assertEvalType("1 + ", ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void varWithExpression() {
    this.assertType("var i = 0", ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void varRef() {
    this.assertType("var i = 0 eval i", ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void varRefToVarDefinedAfter() {
    this.assertType("var i = j var j = i", null);
  }
  
  @Test
  public void testIsInt() {
    Assert.assertTrue(this._expressionTypeComputer.isIntType(ExpressionTypeComputer.INT_TYPE));
  }
  
  @Test
  public void testIsString() {
    Assert.assertTrue(this._expressionTypeComputer.isStringType(ExpressionTypeComputer.STRING_TYPE));
  }
  
  @Test
  public void testIsBool() {
    Assert.assertTrue(this._expressionTypeComputer.isBoolType(ExpressionTypeComputer.BOOL_TYPE));
  }
  
  @Test
  public void testNotIsInt() {
    Assert.assertFalse(this._expressionTypeComputer.isIntType(ExpressionTypeComputer.STRING_TYPE));
  }
  
  @Test
  public void testNotIsString() {
    Assert.assertFalse(this._expressionTypeComputer.isStringType(ExpressionTypeComputer.INT_TYPE));
  }
  
  @Test
  public void testNotIsBool() {
    Assert.assertFalse(this._expressionTypeComputer.isBoolType(ExpressionTypeComputer.INT_TYPE));
  }
  
  public void assertEvalType(final CharSequence input, final ExpressionType expectedType) {
    this.assertType(("eval " + input), expectedType);
  }
  
  public void assertType(final CharSequence input, final ExpressionType expectedType) {
    try {
      this.assertType(IterableExtensions.<AbstractElement>last(this._parseHelper.parse(input).getElements()).getExpression(), expectedType);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void assertType(final Expression e, final ExpressionType expectedType) {
    Assert.assertSame(expectedType, this._expressionTypeComputer.typeFor(e));
  }
}
