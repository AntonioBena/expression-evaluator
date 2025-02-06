package com.expression.evaluator.exception.expression;

import com.expression.evaluator.exception.CustomException;

public class ExpressionNotFoundException extends CustomException {
    public ExpressionNotFoundException(String message) {
        super(message);
    }
}