package com.expression.evaluator.exception.expression;

public class DuplicateExpressionException extends ExpressionException {
    public DuplicateExpressionException(String message) {
        super(message);
    }

    public DuplicateExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}