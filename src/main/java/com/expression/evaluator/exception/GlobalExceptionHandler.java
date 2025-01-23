package com.expression.evaluator.exception;

import com.expression.evaluator.exception.expression.DuplicateExpressionException;
import com.expression.evaluator.exception.expression.ExpressionException;
import com.expression.evaluator.exception.expression.ExpressionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public interface GlobalExceptionHandler {
    ResponseEntity<ErrorResponse> handleExpressionNotFoundException(ExpressionNotFoundException ex);
    ResponseEntity<ErrorResponse> handleDuplicateExpressionException(DuplicateExpressionException ex);

    @ExceptionHandler(ExpressionException.class)
    default ResponseEntity<ErrorResponse> handleExpressionException(ExpressionException ex) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("An error occurred while processing your request.")
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}