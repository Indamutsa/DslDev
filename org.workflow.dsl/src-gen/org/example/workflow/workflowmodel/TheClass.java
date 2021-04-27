/**
 * generated by Xtext 2.24.0
 */
package org.example.workflow.workflowmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>The Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.example.workflow.workflowmodel.TheClass#getName <em>Name</em>}</li>
 *   <li>{@link org.example.workflow.workflowmodel.TheClass#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link org.example.workflow.workflowmodel.TheClass#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @see org.example.workflow.workflowmodel.WorkflowmodelPackage#getTheClass()
 * @model
 * @generated
 */
public interface TheClass extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.example.workflow.workflowmodel.WorkflowmodelPackage#getTheClass_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.example.workflow.workflowmodel.TheClass#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Super Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Super Type</em>' reference.
   * @see #setSuperType(TheClass)
   * @see org.example.workflow.workflowmodel.WorkflowmodelPackage#getTheClass_SuperType()
   * @model
   * @generated
   */
  TheClass getSuperType();

  /**
   * Sets the value of the '{@link org.example.workflow.workflowmodel.TheClass#getSuperType <em>Super Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Super Type</em>' reference.
   * @see #getSuperType()
   * @generated
   */
  void setSuperType(TheClass value);

  /**
   * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
   * The list contents are of type {@link org.example.workflow.workflowmodel.Attribute}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attributes</em>' containment reference list.
   * @see org.example.workflow.workflowmodel.WorkflowmodelPackage#getTheClass_Attributes()
   * @model containment="true"
   * @generated
   */
  EList<Attribute> getAttributes();

} // TheClass