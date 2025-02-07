package com.expression.evaluator.utils;

import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.model.entity.ExpressionEntity;

import java.util.ArrayList;
import java.util.List;

import static com.expression.evaluator.utils.Constants.ALL_PARENTHESIS_REGEX;

public class Utils {

    private Utils() {
    }


    public static ExpressionEntity mapToEntity(ExpressionDto dto) {
        return ExpressionEntity.builder()
                .value(dto.getValue())
                .name(dto.getName())
                .build();
    }

    public static List<String> formatExpressionPart(String[] parts) {
        List<String> p = new ArrayList<>();
        if (parts.length == 1) {
            var part = p.add(parts[0].replaceAll(ALL_PARENTHESIS_REGEX, ""));
            return p;
        }
        for (int index = 0; index < parts.length; index++) {
            var part = parts[index].replaceAll(ALL_PARENTHESIS_REGEX, "");
            p.add(part);
        }
        return p;
    }

    public static String extractLogicalOperator(String expression, String leftPart, String rightPart) {
        var left = expression.indexOf(leftPart) + leftPart.length();
        var right = expression.indexOf(rightPart);

        var operator = expression.substring(left, right).replaceAll(ALL_PARENTHESIS_REGEX, "").trim();
        System.out.println("logical operator value is: " + "-"+operator+"-");
        return operator;
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