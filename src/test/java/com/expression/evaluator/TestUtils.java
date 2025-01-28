package com.expression.evaluator;

import com.expression.evaluator.model.CustomerType;
import com.expression.evaluator.model.dto.AddressDto;
import com.expression.evaluator.model.dto.CustomerDto;
import com.expression.evaluator.model.dto.RequestDto;
import com.expression.evaluator.model.entity.ExpressionEntity;

public class TestUtils {

    public static AddressDto prepareAddress(String city, int houseNumber, String street, String zipCode){
        return AddressDto.builder()
                .city(city)
                .houseNumber(houseNumber)
                .street(street)
                .zipCode(zipCode)
                .build();
    }

    public static CustomerDto prepareCustomer(String firstName, String lastName, int salary, CustomerType type, AddressDto address){
        return CustomerDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .salary(salary)
                .type(type)
                .address(address)
                .build();
    }

    public static RequestDto prepareRequest(CustomerDto customerDto){
        return RequestDto.builder()
                .customer(customerDto)
                .build();
    }

    public static ExpressionEntity prepareExpressionEntity(String expressionName, String value){
        return ExpressionEntity.builder()
                .name(expressionName)
                .value(value)
                .build();
    }
}