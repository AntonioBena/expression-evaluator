package com.expression.evaluator.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotEmpty(message = "City can not be empty")
    private String city;
    @Size(min = 5, max = 5, message = "Zip code must be exactly 5 numbers long")
    @NotNull(message = "Zip code must be exactly 5 numbers long")
    private String zipCode;
    @NotEmpty(message = "Street can not be empty")
    private String street;
    @Min(value = 1, message = "House number must be between 1 and 3 numbers long")
    @Max(value = 99999, message = "House number must be between 1 and 3 numbers long")
    private int houseNumber;
}