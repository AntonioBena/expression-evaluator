package com.expression.evaluator.controller;

import com.expression.evaluator.Base;
import com.expression.evaluator.model.dto.ExpressionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.expression.evaluator.TestUtils.*;
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

    private String postExpression(ExpressionDto request) throws Exception {
        ResultActions result = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));
        result
                .andExpect(status().isOk());
        return result.andReturn().getResponse().getContentAsString();
    }
}