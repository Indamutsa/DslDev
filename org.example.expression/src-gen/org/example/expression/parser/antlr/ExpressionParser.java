/*
 * generated by Xtext 2.24.0
 */
package org.example.expression.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.example.expression.parser.antlr.internal.InternalExpressionParser;
import org.example.expression.services.ExpressionGrammarAccess;

public class ExpressionParser extends AbstractAntlrParser {

	@Inject
	private ExpressionGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalExpressionParser createParser(XtextTokenStream stream) {
		return new InternalExpressionParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "ExpressionsModel";
	}

	public ExpressionGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(ExpressionGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
