/**
 * generated by Xtext 2.24.0
 */
package org.example.entities;

import org.example.entities.EntitiesStandaloneSetupGenerated;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class EntitiesStandaloneSetup extends EntitiesStandaloneSetupGenerated {
  public static void doSetup() {
    new EntitiesStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}
