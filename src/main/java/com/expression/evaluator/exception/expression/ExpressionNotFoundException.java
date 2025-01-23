package com.expression.evaluator.exception.expression;

public class ExpressionNotFoundException extends ExpressionException {
    public ExpressionNotFoundException(String message) {
        super(message);
    }

    public ExpressionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}