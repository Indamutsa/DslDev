package org.example.expression.tests;

import com.google.inject.Inject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.example.expression.expression.ExpressionPackage;
import org.example.expression.expression.ExpressionsModel;
import org.example.expression.tests.ExpressionInjectorProvider;
import org.example.expression.typing.ExpressionType;
import org.example.expression.typing.ExpressionTypeComputer;
import org.example.expression.validation.ExpressionValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(ExpressionInjectorProvider.class)
@SuppressWarnings("all")
public class ExpressionValidatorTest {
  @Inject
  @Extension
  private ParseHelper<ExpressionsModel> _parseHelper;
  
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  @Test
  public void testForwardReferenceInExpression() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("var i = 1 eval j+i var j = 10");
      ExpressionsModel _parse = this._parseHelper.parse(_builder);
      final Procedure1<ExpressionsModel> _function = (ExpressionsModel it) -> {
        this._validationTestHelper.assertError(it, 
          ExpressionPackage.eINSTANCE.getVariableRef(), 
          ExpressionValidator.FORWARD_REFERENCE, 
          "variable forward reference not allowed: \'j\'");
        this._validationTestHelper.assertError(it, 
          ExpressionPackage.eINSTANCE.getExpression(), 
          ExpressionValidator.TYPE_MISMATCH, 
          "null type");
        Assert.assertEquals(2, this._validationTestHelper.validate(it).size());
      };
      ObjectExtensions.<ExpressionsModel>operator_doubleArrow(_parse, _function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testNoForwardReference() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("var j = 10 var i = j");
      this._validationTestHelper.assertNoErrors(this._parseHelper.parse(_builder));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testWrongNotType() {
    this.assertType("!10", ExpressionTypeComputer.INT_TYPE, ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void testWrongMulOrDivType() {
    this.assertType("10 * true", ExpressionTypeComputer.BOOL_TYPE, ExpressionTypeComputer.INT_TYPE);
    this.assertType("\'10\' / 10", ExpressionTypeComputer.STRING_TYPE, ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void testWrongMinusType() {
    this.assertType("10 - true", ExpressionTypeComputer.BOOL_TYPE, ExpressionTypeComputer.INT_TYPE);
    this.assertType("\'10\' - 10", ExpressionTypeComputer.STRING_TYPE, ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void testWrongAndType() {
    this.assertType("10 && true", ExpressionTypeComputer.INT_TYPE, ExpressionTypeComputer.BOOL_TYPE);
    this.assertType("false && \'10\'", ExpressionTypeComputer.STRING_TYPE, ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void testWrongOrType() {
    this.assertType("10 || true", ExpressionTypeComputer.INT_TYPE, ExpressionTypeComputer.BOOL_TYPE);
    this.assertType("false || \'10\'", ExpressionTypeComputer.STRING_TYPE, ExpressionTypeComputer.BOOL_TYPE);
  }
  
  @Test
  public void testWrongEqualityType() {
    this.assertSameType("10 == true", ExpressionTypeComputer.INT_TYPE, ExpressionTypeComputer.BOOL_TYPE);
    this.assertSameType("false != \'10\'", ExpressionTypeComputer.BOOL_TYPE, ExpressionTypeComputer.STRING_TYPE);
  }
  
  @Test
  public void testWrongComparisonType() {
    this.assertSameType("10 < \'1\'", ExpressionTypeComputer.INT_TYPE, ExpressionTypeComputer.STRING_TYPE);
    this.assertSameType("\'10\' > 10", ExpressionTypeComputer.STRING_TYPE, ExpressionTypeComputer.INT_TYPE);
  }
  
  @Test
  public void testWrongBooleanComparison() {
    this.assertNotBooleanType("10 < true");
    this.assertNotBooleanType("false > 0");
    this.assertNotBooleanType("false > true");
  }
  
  @Test
  public void testWrongBooleanPlus() {
    this.assertNotBooleanType("10 + true");
    this.assertNotBooleanType("false + 0");
    this.assertNotBooleanType("false + true");
  }
  
  public void assertType(final CharSequence input, final ExpressionType expectedWrongType, final ExpressionType expectedActualType) {
    try {
      this._validationTestHelper.assertError(this._parseHelper.parse(("eval " + input)), ExpressionPackage.eINSTANCE.getExpression(), ExpressionValidator.TYPE_MISMATCH, 
        ((("expected " + expectedActualType) + " type, but was ") + expectedWrongType));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void assertSameType(final CharSequence input, final ExpressionType expectedLeft, final ExpressionType expectedRight) {
    try {
      this._validationTestHelper.assertError(this._parseHelper.parse(("eval " + input)), ExpressionPackage.eINSTANCE.getExpression(), ExpressionValidator.TYPE_MISMATCH, 
        ((("expected the same type, but was " + expectedLeft) + ", ") + expectedRight));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void assertNotBooleanType(final CharSequence input) {
    try {
      this._validationTestHelper.assertError(this._parseHelper.parse(("eval " + input)), ExpressionPackage.eINSTANCE.getExpression(), ExpressionValidator.TYPE_MISMATCH, 
        "cannot be boolean");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
