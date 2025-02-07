package com.expression.evaluator.evaluator;

import com.expression.evaluator.exception.condition.InvalidConditionException;
import com.expression.evaluator.exception.field.InvalidFieldException;
import com.expression.evaluator.exception.operator.UnsupportedOperatorException;

import java.util.*;

import static com.expression.evaluator.utils.Constants.*;
import static com.expression.evaluator.utils.Utils.*;

public class ExpressionEvaluator {

    public boolean evaluate(String expression, Map<String, Object> validationObject) throws Exception {
        String[] unformattedExpressionParts = expression.split(LOGICAL_REGEX);

        var expressionParts = formatExpressionPart(unformattedExpressionParts);

        var firstResult = evaluateExpression(expressionParts.getFirst().trim(), validationObject);
        for (int index = 1; index < expressionParts.size(); index++) {
            var logicalOperator = extractLogicalOperator(expression, expressionParts.get(index - 1), expressionParts.get(index));
            var secondResult = evaluateExpression(expressionParts.get(index).trim(), validationObject);

            if (logicalOperator.equalsIgnoreCase(OR) || logicalOperator.equals(OR_K)) {
                firstResult = firstResult || secondResult;
            } else if (logicalOperator.equalsIgnoreCase(AND) || logicalOperator.equals(AND_K)) {
                firstResult = firstResult && secondResult;
            } else if (logicalOperator.equalsIgnoreCase(NOT) || logicalOperator.equals(NOT_K)) {
                firstResult = !(firstResult || secondResult);
            }
        }
        return firstResult;
    }

    private boolean evaluateExpression(String expression, Map<String, Object> validationObject) {
        String[] expressionParts;
        boolean result;

        var logicalOperatorMatch = getLogicalOperatorMatch(expression);

        switch (logicalOperatorMatch) {
            case AND:
                expressionParts = expression.split(AND_REGEX).length != 2 ? expression.split(AND_KEYWORD_REGEX) : expression.split(AND_REGEX);
                result = true;
                for (String part : expressionParts) {
                    result = result && evaluateCondition(part.trim(), validationObject);
                }
                break;
            case OR:
                expressionParts = expression.split(OR_REGEX).length != 2 ? expression.split(OR_KEYWORD_REGEX) : expression.split(OR_REGEX);
                result = false;
                for (String part : expressionParts) {
                    result = result || evaluateCondition(part.trim(), validationObject);
                }
                break;
            case NOT:
                expressionParts = expression.split(NOT_REGEX).length != 2 ? expression.split(NOT_KEYWORD_REGEX) : expression.split(NOT_REGEX);
                result = !evaluateCondition(expressionParts[1].trim(), validationObject);
                break;
            default:
                throw new InvalidConditionException("Invalid condition: " + expression);
        }
        return result;
    }

    private String getLogicalOperatorMatch(String expression) {
        if (expression.matches(AND_REGEX_MATCH) || expression.matches(AND_KEYWORD_REGEX_MATCH)) {
            return AND;
        } else if (expression.matches(OR_REGEX_MATCH) || expression.matches(OR_KEYWORD_REGEX_MATCH)) {
            return OR;
        } else if (expression.matches(NOT_REGEX_MATCH) || expression.matches(NOT_KEYWORD_REGEX_MATCH)) {
            return NOT;
        }
        throw new UnsupportedOperatorException("Invalid operator: " + expression);
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
            default -> throw new InvalidConditionException("Invalid condition: " + comparisonOperator);
        };
    }

    private Object resolveValue(Map<String, Object> validationObject, List<String> keys, int keyIndex) {
        if (keyIndex >= keys.size() || validationObject == null) {
            return null;
        }
        var currentKey = keys.get(keyIndex);

        var currentObject = Optional.ofNullable(validationObject.get(currentKey))
                .orElseThrow(() -> new InvalidFieldException("Field " + currentKey + " is not present! "));

        if (keyIndex == keys.size() - 1) {
            return currentObject;
        }
        if (currentObject instanceof Map) {
            return resolveValue((Map<String, Object>) currentObject, keys, keyIndex + 1);
        }
        return null;
    }
}