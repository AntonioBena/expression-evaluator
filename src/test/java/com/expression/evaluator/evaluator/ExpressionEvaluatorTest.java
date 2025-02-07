package com.expression.evaluator.evaluator;

import com.expression.evaluator.Base;
import com.expression.evaluator.exception.condition.InvalidConditionException;
import com.expression.evaluator.exception.field.InvalidFieldException;
import com.expression.evaluator.exception.operator.UnsupportedOperatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorTest extends Base {

    private ExpressionEvaluator expressionEvaluator;

    @BeforeEach
    void setUp() {
        expressionEvaluator = new ExpressionEvaluator();
    }

    @Test
    void test_should_evaluate_simple_AND_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 33, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio && salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), customer);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_simple_AND_and_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 33, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio AND salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), customer);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_simple_OR_and_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 33, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio or salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), customer);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_simple_not_and_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 33, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio not salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), customer);

        assertFalse(result);
    }

    @Test
    void test_should_evaluate_simple_AND_KEYWORD_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 33, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio AND salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), customer);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_Address_simple_AND_expression() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 330, "INDIVIDUAL", address);
        var request = prepareRequest("customer", customer);

        var expression = prepareExpression("simple expression",
                "(customer.address.houseNumber > 300 && customer.salary >= 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), request);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_simple_OR_operator_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio || salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_simple_NOT_operator_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName != Antonio ! salary < 100)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_complex_OR_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);
        var request = prepareRequest("customer", customer);

        var expression = prepareExpression("simple expression",
                "(customer.firstName == Dino && customer.salary >= 100) OR (customer.lastName == test && customer.address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), request);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_complex_OR_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Dino && salary <= 100) OR (lastName == test2" + " && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_evaluate_complex_double_OR_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Dino && salary <= 100) || (lastName == test2" + " && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_evaluate_complex_double_OR_double_AND_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Dino and salary <= 100) || (lastName == test2 ANd address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_evaluate_complex_NOT_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Dino && salary > 100) NOT (lastName == test2" + " && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_complex_NOT_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio && salary > 100) ! (lastName == test && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_evaluate_complex_AND_expression_return_true() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio && salary > 100) AND (lastName == test && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertTrue(result);
    }

    @Test
    void test_should_evaluate_complex_AND_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Dino && salary > 100) AND (lastName == test2 && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_evaluate_complex_double_AND_expression_return_false() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Dino && salary > 100) && (lastName == test2 && address.city == New York)");

        var result = expressionEvaluator.evaluate(expression.getValue(), testObject);

        assertFalse(result);
    }

    @Test
    void test_should_throw_InvalidConditionException_evaluate_simple_expression() {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var testObject = prepareCustomer("Antonio", "test", 130, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName == Antonio ZZ salary > 100)");

        var exception = assertThrows(UnsupportedOperatorException.class,
                () -> expressionEvaluator.evaluate(expression.getValue(), testObject));

        assertEquals("Invalid operator: firstName == Antonio ZZ salary > 100", exception.getMessage());
    }

    @Test
    void test_should_not_evaluate_simple_expression_WRONG() throws Exception {
        var address = prepareAddress("New York", 325, "5th Ave", "65213");
        var customer = prepareCustomer("Antonio", "test", 33, "INDIVIDUAL", address);

        var expression = prepareExpression("simple expression",
                "(firstName =p Antonio && salary d 100)");

        var exception = assertThrows(InvalidConditionException.class,
                () -> expressionEvaluator.evaluate(expression.getValue(), customer));

        assertEquals("Invalid condition: firstName =p Antonio", exception.getMessage());
    }


    @Test
    void test_should_evaluate_complex_OR_expression_return_InvalidFieldException_on_null_fields() {
        var customer = new HashMap<String, Object>();
        customer.put("firstName", "Dino");
        customer.put("lastName", "test");
        customer.put("salary", 130);
        customer.put("type", "INDIVIDUAL");
        customer.put("address", null);

        var request = prepareRequest("customer", customer);

        var expression = prepareExpression("simple expression",
                "(customer.firstName == Dino && customer.salary >= 100) OR (customer.lastName == test && customer.address.city == New York)");

        var exception = assertThrows(InvalidFieldException.class,
                () -> expressionEvaluator.evaluate(expression.getValue(), request));

        assertTrue(exception.getMessage().contains("Field address is not present!"));
    }
}