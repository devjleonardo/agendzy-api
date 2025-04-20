package com.agendzy.api.core.usecase.customer.boundary.input.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerInput {

    @NotBlank(message = "Full name is required.")
    @Size(max = 100, message = "Full name must be at most 100 characters.")
    private String fullName;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 20, message = "Phone number must be at most 20 characters.")
    private String phoneNumber;

    @Email(message = "Email must be valid.")
    @Size(max = 120, message = "Email must be at most 120 characters.")
    private String email;

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

}
