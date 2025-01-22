package com.expression.evaluator.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotEmpty(message = "City can not be empty")
    private String city;
    @Min(value = 5, message = "Zip code must be exactly 5 numbers long")
    @Max(value = 5, message = "Zip code must be exactly 5 numbers long")
    private int zipCode;
    @NotEmpty(message = "Street can not be empty")
    private String street;
    @Min(value = 1, message = "House number must be between 1 and 3 numbers long")
    @Max(value = 3, message = "House number must be between 1 and 3 numbers long")
    private int houseNumber;
}