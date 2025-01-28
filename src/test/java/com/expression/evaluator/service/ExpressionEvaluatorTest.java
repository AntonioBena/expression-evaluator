package com.expression.evaluator.service;

import com.expression.evaluator.model.CustomerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.expression.evaluator.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorTest {

    private ExpressionEvaluator expressionEvaluator;

    @BeforeEach
    void setUp() {
        expressionEvaluator = new ExpressionEvaluator();
    }

    @Test
    void test_should_evaluate_simple_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == " + "Antonio" +  " && salary > 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }


    @Test
    void evaluate() {
    }
}