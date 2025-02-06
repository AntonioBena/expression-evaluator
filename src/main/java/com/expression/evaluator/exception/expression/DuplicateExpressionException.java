package com.expression.evaluator.exception.expression;

import com.expression.evaluator.exception.CustomException;

public class DuplicateExpressionException extends CustomException {
    public DuplicateExpressionException(String message) {
        super(message);
    }
}