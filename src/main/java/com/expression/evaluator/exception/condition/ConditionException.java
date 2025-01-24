package com.expression.evaluator.exception.condition;

public class ConditionException extends RuntimeException {
    public ConditionException(String message) {
        super(message);
    }

    public ConditionException(String message, Throwable cause) {
        super(message, cause);
    }
}