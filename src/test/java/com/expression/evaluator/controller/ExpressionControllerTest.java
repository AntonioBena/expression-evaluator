package com.expression.evaluator.controller;

import com.expression.evaluator.Base;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExpressionControllerTest extends Base {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_should_create_complex_expression_long() throws Exception {
        var request = prepareExpression("simple expression",
                "(customer.firstName == JOHN && customer.salary < 100) NOT (customer.address != null && customer.address.city == Washington)");

        var result = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        var uuid = result.andReturn().getResponse().getContentAsString();

        assertEquals(36, uuid.length());
    }
    @Test
    void test_should_not_create_complex_expression_without_attributes() throws Exception {
        var request = prepareExpression("", "");

        var result = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

        var response = result.andReturn().getResponse().getContentAsString();

        Map<String, String> errorMap = mapper.readValue(response, new TypeReference<>() {});

        assertTrue(errorMap.containsKey("name"));
        assertTrue(errorMap.containsKey("value"));
    }


    @Test
    void test_should_not_create_complex_expression_long_with_same_name() throws Exception {
        var request = prepareExpression("complex expression long with not",
                "(customer.firstName == JOHN && customer.salary < 100) NOT (customer.address != null && customer.address.city == Washington)");

        var request1 = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        var request2 = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        var uuid = request1
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().length();

        var message = request2
                .andExpect(status().is4xxClientError());

        assertEquals(36, uuid);
    }
}