package com.expression.evaluator.model.dto;

import com.expression.evaluator.model.CustomerType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerDto {
    @NotEmpty(message = "First name can not be empty")
    @Size(min = 2, max = 50)
    private String firstName;
    @NotEmpty(message = "Last name can not be empty")
    @Size(min = 2, max = 50)
    private String lastName;
    @Valid
    private AddressDto address;
    @NotNull
    @Min(value = 1, message = "Salary value can not be smaller than 1")
    private int salary;
    @NotNull(message = "Customer type can not be empty")
    private CustomerType type;
}