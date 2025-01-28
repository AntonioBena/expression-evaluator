package com.expression.evaluator.evaluator;

import com.expression.evaluator.exception.condition.InvalidConditionException;
import com.expression.evaluator.exception.expression.ExpressionException;
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
    void test_should_evaluate_simple_AND_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 33, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == Antonio && salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_Address_simple_AND_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 330, CustomerType.INDIVIDUAL, address);
        var request = prepareRequest(customer);

        var expression = prepareExpressionEntity("simple expression",
                "(customer.address.houseNumber > 300 && customer.salary >= 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), request);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_simple_OR_operator_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == Antonio || salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_simple_NOT_operator_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName != Antonio ! salary < 100)"); //TODO

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_complex_OR_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);
        var request = prepareRequest(customer);

        var expression = prepareExpressionEntity("simple expression",
                "(customer.firstName == Dino && customer.salary >= 100) OR (customer.lastName == test && customer.address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), request);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_complex_OR_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == Dino && salary <= 100) OR (lastName == test2" + " && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_evaluate_complex_NOT_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == Dino && salary > 100) NOT (lastName == test2" + " && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_complex_NOT_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == Antonio && salary > 100) NOT (lastName == test && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_evaluate_complex_AND_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == Antonio && salary > 100) AND (lastName == test && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_complex_AND_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == Dino && salary > 100) AND (lastName == test2 && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_throw_InvalidConditionException_evaluate_simple_expression() {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, address);

        var expression = prepareExpressionEntity("simple expression",
                "(firstName == Antonio ZZ salary > 100)");

        var exception = assertThrows(InvalidConditionException.class,
                () -> expressionEvaluator.evaluate(expression.getValue(), testObject));

        assertEquals("Invalid condition: firstName == Antonio ZZ salary > 100", exception.getMessage());
    }


    @Test
    void test_should_evaluate_complex_OR_expression_return_ExpressionException() throws Exception {
        var customer = prepareCustomer("Antonio", "test", 130, CustomerType.INDIVIDUAL, null);
        var request = prepareRequest(customer);

        var expression = prepareExpressionEntity("simple expression",
                "(customer.firstName == Dino && customer.salary >= 100) OR (customer.lastName == test && customer.address.city == New York)");

        var exception = assertThrows(ExpressionException.class,
                () -> expressionEvaluator.evaluate(expression.getValue(), request));

        assertEquals("Can not process your request because you gave me null fields!", exception.getMessage());
    }
}