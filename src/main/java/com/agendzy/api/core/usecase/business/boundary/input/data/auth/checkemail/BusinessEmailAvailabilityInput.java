package com.agendzy.api.core.usecase.business.boundary.input.data.auth.checkemail;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessEmailAvailabilityInput {

    @NotBlank(message = "Email is required.")
    private String email;

}
