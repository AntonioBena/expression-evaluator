package com.expression.evaluator.exception.field;

import com.expression.evaluator.exception.CustomException;

public class InvalidFieldException extends CustomException {
    public InvalidFieldException(String message) {
        super(message);
    }
}