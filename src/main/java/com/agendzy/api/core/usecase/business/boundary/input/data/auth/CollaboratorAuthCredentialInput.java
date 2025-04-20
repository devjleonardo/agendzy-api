package com.agendzy.api.core.usecase.business.boundary.input.data.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollaboratorAuthCredentialInput {

    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password name is required.")
    private String password;

    private String businessId;

}
