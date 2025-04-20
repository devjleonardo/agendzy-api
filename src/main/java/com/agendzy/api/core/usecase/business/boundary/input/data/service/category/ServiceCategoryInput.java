package com.agendzy.api.core.usecase.business.boundary.input.data.service.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceCategoryInput {

    @NotBlank(message = "Name is required.")
    private String name;

    private boolean active;

}
