package com.expression.evaluator.evaluator;

import com.expression.evaluator.exception.condition.InvalidConditionException;
import com.expression.evaluator.exception.field.InvalidFieldException;
import com.expression.evaluator.exception.operator.UnsupportedOperatorException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.expression.evaluator.utils.Constants.*;
import static com.expression.evaluator.utils.Utils.extractAndFormatCondition;
import static com.expression.evaluator.utils.Utils.extractLogicalOperator;

public class ExpressionEvaluator {

    public boolean evaluate(String expression, Map<String, Object> validationObject) throws Exception {
        String[] expressionParts = expression.split(LOGICAL_REGEX);

        var firstResult = evaluateExpression(expressionParts[0].trim(), validationObject);
        for (int index = 1; index < expressionParts.length; index++) {
            var logicalOperator = extractLogicalOperator(expression, expressionParts[index - 1], expressionParts[index]);
            var secondResult = evaluateExpression(expressionParts[index].trim(), validationObject);

            if (logicalOperator.equalsIgnoreCase(OR)) {
                firstResult = firstResult || secondResult;
            } else if (logicalOperator.equalsIgnoreCase(AND)) {
                firstResult = firstResult && secondResult;
            } else if (logicalOperator.equalsIgnoreCase(NOT)) {
                firstResult = !(firstResult || secondResult);
            }
        }
        return firstResult;
    }

    private boolean evaluateExpression(String expression, Map<String, Object> validationObject) {
        expression = expression.trim();
        if (expression.startsWith(LEFT_PARENTHESIS) && expression.endsWith(RIGHT_PARENTHESIS)) {
            expression = expression.substring(1, expression.length() - 1);
        }

        String[] expressionParts;
        boolean result;

        var logicalOperatorMatch = getLogicalOperatorMatch(expression);

        switch (logicalOperatorMatch) {
            case AND:
                expressionParts = expression.split(AND_REGEX);
                result = true;
                for (String part : expressionParts) {
                    result = result && evaluateCondition(part.trim(), validationObject);
                }
                break;
            case OR:
                expressionParts = expression.split(OR_REGEX);
                result = false;
                for (String part : expressionParts) {
                    result = result || evaluateCondition(part.trim(), validationObject);
                }
                break;
            case NOT:
                expressionParts = expression.split(NOT_REGEX);
                result = !evaluateCondition(expressionParts[1].trim(), validationObject);
                break;
            default:
                throw new InvalidConditionException("Invalid condition: " + expression);
        }
        return result;
    }

    private String getLogicalOperatorMatch(String expression) {
        if (expression.matches(AND_REGEX_MATCH)) {
            return AND;
        } else if (expression.matches(OR_REGEX_MATCH)) {
            return OR;
        } else if (expression.matches(NOT_REGEX_MATCH)) {
            return NOT;
        }
        throw new InvalidConditionException("Invalid condition: " + expression);
    }

    private boolean evaluateCondition(String condition, Map<String, Object> validationObject) {
        String[] variableParts = condition.split(CONDITIONS_REGEX);
        if (variableParts.length != 2) {
            throw new InvalidConditionException("Invalid condition: " + condition);
        }
        var expressionVariableName = variableParts[0].trim();
        var expressionVariableValue = variableParts[1].trim();

        var inputObjectVariableValue =
                resolveValue(validationObject, List.of(expressionVariableName.split(DOT_REGEX)), 0);

        var comparisonOperator =
                extractAndFormatCondition(condition, expressionVariableName, expressionVariableValue);

        return switch (comparisonOperator) {
            case EQUAL -> inputObjectVariableValue.toString().equals(expressionVariableValue);
            case NOT_EQUAL -> !inputObjectVariableValue.toString().equals(expressionVariableValue);
            case LESS_THAN ->
                    Double.parseDouble(inputObjectVariableValue.toString()) < Double.parseDouble(expressionVariableValue);
            case MORE_THAN ->
                    Double.parseDouble(inputObjectVariableValue.toString()) > Double.parseDouble(expressionVariableValue);
            case LESS_THAN_OR_EQUAL ->
                    Double.parseDouble(inputObjectVariableValue.toString()) <= Double.parseDouble(expressionVariableValue);
            case MORE_THAN_OR_EQUAL ->
                    Double.parseDouble(inputObjectVariableValue.toString()) >= Double.parseDouble(expressionVariableValue);
            default -> throw new UnsupportedOperatorException("Unsupported operator: " + comparisonOperator);
        };
    }

    private Object resolveValue(Map<String, Object> validationObject, List<String> keys, int keyIndex) {
        if (keyIndex >= keys.size() || validationObject == null) {
            return null;
        }
        var currentKey = keys.get(keyIndex);

        var currentObject = Optional.ofNullable(validationObject.get(currentKey))
                .orElseThrow(()-> new InvalidFieldException("Field " + currentKey + " is not present! "));

        if (keyIndex == keys.size() - 1) {
            return currentObject;
        }
        if (currentObject instanceof Map) {
            return resolveValue((Map<String, Object>) currentObject, keys, keyIndex + 1);
        }
        return null;
    }
}