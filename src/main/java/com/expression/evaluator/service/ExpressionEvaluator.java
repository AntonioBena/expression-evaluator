package com.expression.evaluator.service;

import com.expression.evaluator.exception.condition.InvalidConditionException;
import com.expression.evaluator.exception.operator.UnsupportedOperatorException;

import static com.expression.evaluator.utils.Constants.*;

public class ExpressionEvaluator {

    public boolean evaluate(String expression, Object validationObject) throws Exception {
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

    private boolean evaluateExpression(String expression, Object validationObject) throws Exception {
        expression = expression.trim();
        if (expression.startsWith("(") && expression.endsWith(")")) {
            expression = expression.substring(1, expression.length() - 1);
        }

        String[] expressionParts = expression.split("(?i)\\s&&\\s|\\s!\\s"); //TODO consider adding &&, |, !
        var result = true;

        for (String andPart : expressionParts) {
            String[] orParts = andPart.split("\\|\\|");
            var orResult = false;

            for (String orPart : orParts) {
                orResult = orResult || evaluateCondition(orPart.trim(), validationObject);
            }
            result = result && orResult;
        }
        return result;
    }

    private String extractLogicalOperator(String expression, String leftPart, String rightPart) {
        var leftEnd = expression.indexOf(leftPart) + leftPart.length();
        var rightStart = expression.indexOf(rightPart);

        return expression.substring(leftEnd, rightStart).trim();
    }

    private boolean evaluateCondition(String condition, Object validationObject) throws Exception {
        String[] variableParts = condition.split(CONDITIONS_REGEX);
        if (variableParts.length != 2) {
            throw new InvalidConditionException("Invalid condition: " + condition);
        }

        var expressionVariableName = variableParts[0].trim();
        var expressionVariableValue = variableParts[1].trim().replace("\"", "");

        var preOperator = condition.replace(expressionVariableName, "")
                .replace(expressionVariableValue, "").trim();

        Object inputObjectVariableValue = resolveValue(expressionVariableName, validationObject);

        var op = preOperator.replace("\"", "");
        var comparisonOperator = op.replace(" ", "");

        return switch (comparisonOperator) {
            case EQUAL ->
                    inputObjectVariableValue.toString().equals(expressionVariableValue);
            case NOT_EQUAL ->
                    !inputObjectVariableValue.toString().equals(expressionVariableValue);
            case LESS_THAN ->
                    Double.parseDouble(inputObjectVariableValue.toString()) < Double.parseDouble(expressionVariableValue);
            case MORE_THAN ->
                    Double.parseDouble(inputObjectVariableValue.toString()) > Double.parseDouble(expressionVariableValue);
            case LESS_THAN_OR_EQUAL ->
                    Double.parseDouble(inputObjectVariableValue.toString()) <= Double.parseDouble(expressionVariableValue);
            case MORE_THAN_OR_EQUAL ->
                    Double.parseDouble(inputObjectVariableValue.toString()) >= Double.parseDouble(expressionVariableValue);
            default ->
                    throw new UnsupportedOperatorException("Unsupported operator: " + comparisonOperator);
        };
    }

    private Object resolveValue(String inputJsonFieldPath, Object validationObject) throws Exception {
        String[] fields = inputJsonFieldPath.split("\\.");
        Object currentObject = validationObject;

        for (String field : fields) {
            var declaredField = currentObject.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            currentObject = declaredField.get(currentObject);
        }
        return currentObject;
    }
}