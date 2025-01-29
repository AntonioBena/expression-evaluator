package com.expression.evaluator.exception.operator;

import com.expression.evaluator.exception.CustomException;

public class UnsupportedOperatorException extends CustomException {
    public UnsupportedOperatorException(String message) {
        super(message);
    }

    public UnsupportedOperatorException(String message, Throwable cause) {
        super(message, cause);
    }
}