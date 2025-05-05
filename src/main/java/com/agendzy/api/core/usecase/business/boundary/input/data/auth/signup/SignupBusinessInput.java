package com.agendzy.api.core.usecase.business.boundary.input.data.auth.signup;

import com.agendzy.api.core.domain.business.BusinessTeamSize;
import com.agendzy.api.core.usecase.common.boundary.input.data.user.CreateUserInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupBusinessInput {

    @NotBlank(message = "Business name is required.")
    private String businessName;

    @NotNull(message = "Business team size is required.")
    private BusinessTeamSize businessTeamSize;

    @NotNull(message = "User is required")
    private CreateUserInput user;

}
