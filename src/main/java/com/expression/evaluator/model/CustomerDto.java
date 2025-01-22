package com.expression.evaluator.model;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerDto {
    private String firstName;
    private String lastName;
    private AddressDto address;
    private int salary;
    private CustomerType type;
}