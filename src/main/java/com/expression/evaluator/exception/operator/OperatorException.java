package com.expression.evaluator.exception.operator;

public class OperatorException extends RuntimeException {
    public OperatorException(String message) {
        super(message);
    }

    public OperatorException(String message, Throwable cause) {
        super(message, cause);
    }
}