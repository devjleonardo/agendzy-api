package com.agendzy.api.core.usecase.customer.boundary.input.data.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAuthCredentialInput {

    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password name is required.")
    private String password;

}
