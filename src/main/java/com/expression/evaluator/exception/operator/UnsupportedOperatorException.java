package com.expression.evaluator.exception.operator;

public class UnsupportedOperatorException extends OperatorException{
    public UnsupportedOperatorException(String message) {
        super(message);
    }

    public UnsupportedOperatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
