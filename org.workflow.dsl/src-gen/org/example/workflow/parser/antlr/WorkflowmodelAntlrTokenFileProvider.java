/*
 * generated by Xtext 2.24.0
 */
package org.example.workflow.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class WorkflowmodelAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("org/example/workflow/parser/antlr/internal/InternalWorkflowmodel.tokens");
	}
}