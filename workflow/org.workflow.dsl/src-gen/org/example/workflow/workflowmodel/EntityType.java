/**
 * generated by Xtext 2.24.0
 */
package org.example.workflow.workflowmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.example.workflow.workflowmodel.EntityType#getEntity <em>Entity</em>}</li>
 * </ul>
 *
 * @see org.example.workflow.workflowmodel.WorkflowmodelPackage#getEntityType()
 * @model
 * @generated
 */
public interface EntityType extends EObject
{
  /**
   * Returns the value of the '<em><b>Entity</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Entity</em>' reference.
   * @see #setEntity(TheClass)
   * @see org.example.workflow.workflowmodel.WorkflowmodelPackage#getEntityType_Entity()
   * @model
   * @generated
   */
  TheClass getEntity();

  /**
   * Sets the value of the '{@link org.example.workflow.workflowmodel.EntityType#getEntity <em>Entity</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Entity</em>' reference.
   * @see #getEntity()
   * @generated
   */
  void setEntity(TheClass value);

} // EntityType
