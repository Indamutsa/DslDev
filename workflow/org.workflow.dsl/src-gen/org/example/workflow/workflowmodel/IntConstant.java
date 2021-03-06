/**
 * generated by Xtext 2.24.0
 */
package org.example.workflow.workflowmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Int Constant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.example.workflow.workflowmodel.IntConstant#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.example.workflow.workflowmodel.WorkflowmodelPackage#getIntConstant()
 * @model
 * @generated
 */
public interface IntConstant extends Expression
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(int)
   * @see org.example.workflow.workflowmodel.WorkflowmodelPackage#getIntConstant_Value()
   * @model
   * @generated
   */
  int getValue();

  /**
   * Sets the value of the '{@link org.example.workflow.workflowmodel.IntConstant#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(int value);

} // IntConstant
