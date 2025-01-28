package com.expression.evaluator.controller;

import com.expression.evaluator.Base;
import com.expression.evaluator.model.CustomerType;
import com.expression.evaluator.model.dto.ExpressionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class EvaluationControllerTest extends Base {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_should_evaluate_complex_AND_expression() throws Exception {
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName != JOHN && customer.salary >= 100) AND (customer.address != null && customer.address.city == Washington)");

        var expressionUuid = postExpression(expressionRequest);

        var customerRequest = prepareCustomerEvaluation("Toni", "test",
                25, CustomerType.BUSINESS, "Washington", "56th Ave", 542, "12345");

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content( mapper.writeValueAsString(customerRequest) ));

        assertNotNull(expressionUuid);

        result.andExpect(status().isOk());
        var response = result.andReturn().getResponse().getContentAsString();

        assertEquals("Evaluation result of expression: " + expressionRequest.getValue() + " is: false", response);
    }

    @Test
    void test_should_evaluate_complex_OR_expression() throws Exception {//TODO
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName != JOHN || customer.salary >= 100) OR (customer.address != null && customer.address.city == New York)");

        var expressionUuid = postExpression(expressionRequest);

        var customerRequest = prepareCustomerEvaluation("Toni", "test",
                25, CustomerType.BUSINESS, "Washington", "56th Ave", 542, "12345");

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content( mapper.writeValueAsString(customerRequest) ));

        assertNotNull(expressionUuid);

        result.andExpect(status().isOk());
        var response = result.andReturn().getResponse().getContentAsString();

        assertEquals("Evaluation result of expression: " + expressionRequest.getValue() + " is: true", response);
    }

    @Test
    void test_should_throw_InvalidConditionException_complex_AND_expression() throws Exception {
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName = JOHN && customer.salary >= 100) D (customer.address != null && customer.address.city == Washington)");

        var expressionUuid = postExpression(expressionRequest);

        var customerRequest = prepareCustomerEvaluation("Toni", "test",
                25, CustomerType.BUSINESS, "Washington", "56th Ave", 542, "12345");

        assertNotNull(expressionUuid);

        assertThrows(ServletException.class, ()-> {
            var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content( mapper.writeValueAsString(customerRequest) ));

            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Invalid condition"));
        });
    }

    @Test
    void test_should_evaluate_simple_AND_expression() throws Exception {
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName == JOHN && customer.salary < 100)");

        var expressionUuid = postExpression(expressionRequest);

        var customerRequest = prepareCustomerEvaluation("JOHN", "test",
                25, CustomerType.BUSINESS, "New York", "56th Ave", 542, "12345");

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content( mapper.writeValueAsString(customerRequest) ));

        assertNotNull(expressionUuid);

        result.andExpect(status().isOk());
        var response = result.andReturn().getResponse().getContentAsString();

        assertEquals("Evaluation result of expression: " + expressionRequest.getValue() + " is: true", response);
    }

    @Test
    void test_should_evaluate_simple_OR_expression() throws Exception {
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName == JOHN || customer.salary < 100)");

        var expressionUuid = postExpression(expressionRequest);

        var customerRequest = prepareCustomerEvaluation("Toni", "test",
                25, CustomerType.BUSINESS, "New York", "56th Ave", 542, "12345");

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content( mapper.writeValueAsString(customerRequest) ));

        assertNotNull(expressionUuid);

        result.andExpect(status().isOk());
        var response = result.andReturn().getResponse().getContentAsString();

        assertEquals("Evaluation result of expression: " + expressionRequest.getValue() + " is: true", response);
    }


    @Test
    void test_should_evaluate_simple_NOT_expression() throws Exception {
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName == JOHN ! customer.salary < 100)");

        var expressionUuid = postExpression(expressionRequest);

        var customerRequest = prepareCustomerEvaluation("JOHN", "test",
                25, CustomerType.BUSINESS, "New York", "56th Ave", 542, "12345");

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content( mapper.writeValueAsString(customerRequest) ));

        assertNotNull(expressionUuid);

        result.andExpect(status().isOk());
        var response = result.andReturn().getResponse().getContentAsString();

        assertEquals("Evaluation result of expression: " + expressionRequest.getValue() + " is: false", response);
    }



    private String postExpression(ExpressionDto request) throws Exception {
        ResultActions result = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));
        result
                .andExpect(status().isOk());
        return result.andReturn().getResponse().getContentAsString();
    }
}