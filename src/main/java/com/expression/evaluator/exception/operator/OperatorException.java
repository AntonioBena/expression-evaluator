package com.expression.evaluator.exception.operator;

import com.expression.evaluator.exception.CustomException;

public class OperatorException extends CustomException {
    public OperatorException(String message) {
        super(message);
    }

    public OperatorException(String message, Throwable cause) {
        super(message, cause);
    }
}