package org.example.expression.typing;

import org.example.expression.typing.ExpressionType;

@SuppressWarnings("all")
public class StringType implements ExpressionType {
  @Override
  public String toString() {
    return "string";
  }
}
