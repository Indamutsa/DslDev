/**
 * generated by Xtext 2.24.0
 */
package org.example.expression.expression;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bool Constant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.example.expression.expression.BoolConstant#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.example.expression.expression.ExpressionPackage#getBoolConstant()
 * @model
 * @generated
 */
public interface BoolConstant extends Expression
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(String)
   * @see org.example.expression.expression.ExpressionPackage#getBoolConstant_Value()
   * @model
   * @generated
   */
  String getValue();

  /**
   * Sets the value of the '{@link org.example.expression.expression.BoolConstant#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(String value);

} // BoolConstant
