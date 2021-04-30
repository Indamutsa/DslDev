package org.example.expression.ui.hover;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.eclipse.xtext.xbase.lib.Extension;
import org.example.expression.expression.Expression;
import org.example.expression.interpreter.ExpressionInterpreter;
import org.example.expression.typing.ExpressionTypeComputer;

/**
 * Remember that our interpreter is based on the assumption that it is invoked only on
 * an EMF model that contains no error. We invoke our validator programmatically
 * using the EMF API that is, the Diagnostician class.
 */
@SuppressWarnings("all")
public class ExpressionEObjectHoverProvider extends DefaultEObjectHoverProvider {
  @Inject
  @Extension
  private ExpressionTypeComputer _expressionTypeComputer;
  
  @Inject
  @Extension
  private ExpressionInterpreter _expressionInterpreter;
  
  @Override
  public String getHoverInfoAsHtml(final EObject o) {
    if (((o instanceof Expression) && this.programHasNoError(o))) {
      final Expression exp = ((Expression) o);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<p>");
      _builder.newLine();
      _builder.append("type  : <b>");
      String _string = this._expressionTypeComputer.typeFor(exp).toString();
      _builder.append(_string);
      _builder.append("</b> <br>");
      _builder.newLineIfNotEmpty();
      _builder.append("value : <b>");
      String _string_1 = this._expressionInterpreter.interpret(exp).toString();
      _builder.append(_string_1);
      _builder.append("</b>");
      _builder.newLineIfNotEmpty();
      _builder.append("</p>");
      _builder.newLine();
      return _builder.toString();
    } else {
      return super.getHoverInfoAsHtml(o);
    }
  }
  
  public boolean programHasNoError(final EObject o) {
    return Diagnostician.INSTANCE.validate(EcoreUtil.getRootContainer(o)).getChildren().isEmpty();
  }
}
