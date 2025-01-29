package com.expression.evaluator.exception.condition;

import com.expression.evaluator.exception.CustomException;

public class InvalidConditionException extends CustomException {

    public InvalidConditionException(String message) {
        super(message);
    }
}