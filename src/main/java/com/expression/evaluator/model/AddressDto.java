package com.expression.evaluator.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String city;
    private int zipCode;
    private String street;
    private int houseNumber;
}