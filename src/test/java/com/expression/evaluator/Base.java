package com.expression.evaluator;

import com.expression.evaluator.model.CustomerType;
import com.expression.evaluator.model.dto.AddressDto;
import com.expression.evaluator.model.dto.CustomerDto;
import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.model.dto.RequestDto;
import com.expression.evaluator.repository.ExpressionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class Base {

    @Autowired
    private ExpressionRepository expressionRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        expressionRepository.deleteAll();
    }

    public ExpressionDto prepareExpression(String name, String value){
        return ExpressionDto.builder()
                .name(name)
                .value(value)
                .build();
    }

    public RequestDto prepareCustomerEvaluation(String firstName, String lastName,
                                                int salary, CustomerType type, AddressDto address){
        var customer = CustomerDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .salary(salary)
                .type(type)
                .address(address)
                .build();
        return RequestDto.builder()
                .customer(customer)
                .build();
    }
    public RequestDto prepareCustomerEvaluation(String firstName, String lastName, int salary, CustomerType type,
                                                String city, String street, int houseNumber, String zipCode){
        var address = AddressDto.builder()
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();
        var customer = CustomerDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .salary(salary)
                .type(type)
                .address(address)
                .build();
        return RequestDto.builder()
                .customer(customer)
                .build();
    }
}
