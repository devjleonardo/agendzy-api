package com.agendzy.api.core.usecase.business.boundary.input.data.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessProfileInput {

    @NotBlank(message = "Photo URL is required.")
    private String profilePhotoUrl;

}
