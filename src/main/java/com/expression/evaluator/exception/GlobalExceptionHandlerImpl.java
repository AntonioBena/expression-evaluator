package com.expression.evaluator.exception;

import com.expression.evaluator.exception.expression.DuplicateExpressionException;
import com.expression.evaluator.exception.expression.ExpressionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandlerImpl implements GlobalExceptionHandler{

    @ExceptionHandler(ExpressionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpressionNotFoundException(ExpressionNotFoundException ex) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DuplicateExpressionException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateExpressionException(DuplicateExpressionException ex) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .httpStatus(HttpStatus.CONFLICT.value())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}