package com.expression.evaluator.exception.expression;

import com.expression.evaluator.exception.CustomException;

public class ExpressionException extends CustomException {
    public ExpressionException(String message) {
        super(message);
    }

    public ExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}