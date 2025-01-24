package com.expression.evaluator.exception;

import com.expression.evaluator.exception.condition.InvalidConditionException;
import com.expression.evaluator.exception.expression.DuplicateExpressionException;
import com.expression.evaluator.exception.expression.ExpressionNotFoundException;
import com.expression.evaluator.exception.operator.UnsupportedOperatorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandlerImpl implements GlobalExceptionHandler{

    @Override
    @ExceptionHandler(ExpressionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpressionNotFoundException(ExpressionNotFoundException ex) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @Override
    @ExceptionHandler(DuplicateExpressionException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateExpressionException(DuplicateExpressionException ex) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .httpStatus(HttpStatus.CONFLICT.value())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @Override
    public ResponseEntity<ErrorResponse> handleUnsupportedOperatorException(UnsupportedOperatorException ex) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    public ResponseEntity<ErrorResponse> handleInvalidConditionException(InvalidConditionException ex) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}