package com.expression.evaluator.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExpressionDto {
    private String uuid;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Value should not be empty")
    private String value;
}