package com.expression.evaluator.utils;

import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.model.entity.ExpressionEntity;

public class Utils {

    private Utils() {
    }

    public static ExpressionDto mapToDto(ExpressionEntity entity) {
        return ExpressionDto.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .value(entity.getValue())
                .build();
    }

    public static ExpressionEntity mapToEntity(ExpressionDto dto) {
        return ExpressionEntity.builder()
                .value(dto.getValue())
                .name(dto.getName())
                .build();
    }

    public static String removeParentheses(String inputValue) {
        return inputValue.replace("(", "").replace(")", "");
    }

    public static String extractLogicalOperator(String expression, String leftPart, String rightPart) {
        var left = expression.indexOf(leftPart) + leftPart.length();
        var right = expression.indexOf(rightPart);

        return expression.substring(left, right).trim();
    }

    public static String extractAndFormatCondition(String condition, String expressionName, String expressionValue) {
        return condition
                .replace(expressionName, "")
                .replace(expressionValue
                        .replace("\"", ""), "")
                .trim()
                .replace("\"", "")
                .replace(" ", "");
    }
}