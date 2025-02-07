package com.expression.evaluator.controller;

import com.expression.evaluator.Base;
import com.expression.evaluator.exception.condition.InvalidConditionException;
import com.expression.evaluator.exception.field.InvalidFieldException;
import com.expression.evaluator.model.dto.ExpressionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class EvaluationControllerTest extends Base {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_should_evaluate_complex_AND_expression_Customer() throws Exception {
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName != JOHN && customer.salary >= 100) AND (customer.address != null && customer.address.city == Washington)");

        var expressionUuid = postExpression(expressionRequest);

        var address = prepareAddress("Washington", 20, "5th Ave", "45621");
        var customer = prepareCustomer("Toni", "test", 25, "INDIVIDUAL", address);
        var request = prepareRequest("customer", customer);

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        assertNotNull(expressionUuid);

        result.andExpect(status().isOk());
        var response = result.andReturn().getResponse().getContentAsString();

        assertEquals("Evaluation result of expression: " + expressionRequest.getValue() + " is: false", response);
    }

    @Test
    void test_should_evaluate_complex_AND_expression_Vehicle() throws Exception {
        var expressionRequest = prepareExpression("simple expression",
                "(vehicle.model == Mercedes && vehicle.isNew == true) AND " +
                        "(vehicle.vehicleExtendedInfo.wheelsSize >= 19 && vehicle.vehicleExtendedInfo.frontTyreInfo.tireWidth > 195)");
        var expressionUuid = postExpression(expressionRequest);

        var address = prepareAddress("Washington", 20, "5th Ave", "45621");
        var customer = prepareCustomer("Toni", "test", 25, "INDIVIDUAL", address);
        var vehicle = prepareVehicleWithVehicleDefaults("Mercedes", 105000, true, customer);
        var request = prepareRequest("vehicle", vehicle);

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        assertNotNull(expressionUuid);

        result.andExpect(status().isOk());
        var response = result.andReturn().getResponse().getContentAsString();

        assertEquals("Evaluation result of expression: " + expressionRequest.getValue() + " is: true", response);
    }

    @Test
    void test_should_evaluate_complex_OR_expression() throws Exception {//TODO
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName != JOHN || customer.salary >= 100) OR (customer.address != null && customer.address.city == New York)");

        var expressionUuid = postExpression(expressionRequest);

        var address = prepareAddress("New York", 256, "5th Ave", "45621");
        var customer = prepareCustomer("JOHN", "test", 100, "INDIVIDUAL", address);
        var request = prepareRequest("customer", customer);

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

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

        var customer = prepareCustomer("JOGN", "test", 40, "BUSINESS");
        var request = prepareRequest("customer", customer);

        assertNotNull(expressionUuid);

        assertThatThrownBy(() ->
                mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer)))
                        .andExpect(status().isBadRequest()))
                .hasCauseInstanceOf(InvalidConditionException.class)
                .hasMessageContaining("Invalid condition");
    }

    @Test
    void test_should_evaluate_simple_AND_expression() throws Exception {
        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName == JOHN && customer.salary < 100)");

        var expressionUuid = postExpression(expressionRequest);

        var address = prepareAddress("New York", 256, "56th Ave", "12345");
        var customer = prepareCustomer("JOHN", "test", 99, "BUSINESS", address);
        var request = prepareRequest("customer", customer);

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

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

        var address = prepareAddress("New York", 256, "56th Ave", "12345");
        var customer = prepareCustomer("toni", "test", 99, "BUSINESS", address);
        var request = prepareRequest("customer", customer);

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

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

        var customer = prepareCustomer("JOHN", "test", 55, "BUSINESS");
        var request = prepareRequest("customer", customer);

        var result = mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        assertNotNull(expressionUuid);

        result.andExpect(status().isOk());
        var response = result.andReturn().getResponse().getContentAsString();

        assertEquals("Evaluation result of expression: " + expressionRequest.getValue() + " is: false", response);
    }

    @Test
    void test_should_throw_InvalidFieldException_exception_on_empty_request() {
        var emptyRequest = new HashMap<>();

        assertThatThrownBy(() ->
                mockMvc.perform(post("/evaluate?uuid=" + "expressionUuid")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(emptyRequest)))
                        .andExpect(status().isBadRequest()))
                .hasCauseInstanceOf(InvalidFieldException.class)
                .hasMessageContaining("Object does not contain any key or value!");
    }

    @Test
    void test_should_throw_InvalidFieldException_exception_on_empty_address() throws Exception {
        var customer = prepareCustomer("JOHN", "test", 55, "BUSINESS");
        var request = prepareRequest("customer", customer);

        var expressionRequest = prepareExpression("simple expression",
                "(customer.firstName != JOHN && customer.salary >= 100) AND (customer.address.zipCode == 55160 && customer.address.city == Washington)");
        var expressionUuid = postExpression(expressionRequest);

        assertThatThrownBy(() ->
                mockMvc.perform(post("/evaluate?uuid=" + expressionUuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
                        .andExpect(status().isBadRequest()))
                .hasCauseInstanceOf(InvalidFieldException.class)
                .hasMessageContaining("Field address is not present! ");
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