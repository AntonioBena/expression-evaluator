package com.expression.evaluator.exception.condition;

public class InvalidConditionException extends ConditionException{
    public InvalidConditionException(String message) {
        super(message);
    }

    public InvalidConditionException(String message, Throwable cause) {
        super(message, cause);
    }
}
