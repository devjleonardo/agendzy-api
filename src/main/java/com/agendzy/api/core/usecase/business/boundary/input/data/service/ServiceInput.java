package com.agendzy.api.core.usecase.business.boundary.input.data.service;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ServiceInput {

    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String name;

    @Size(max = 1000, message = "Description must be at most 1000 characters.")
    private String description;

    @NotNull(message = "Duration in minutes is required.")
    @Min(value = 1, message = "Duration must be at least 1 minute.")
    private Long durationInMinutes;

    @NotNull(message = "Price is required.")
    @PositiveOrZero(message = "Price must be zero or positive.")
    private Double price;

    @PositiveOrZero(message = "Promotional price must be zero or positive.")
    private Double promotionalPrice;

    @Size(max = 10, message = "Color hex must be at most 10 characters.")
    private String colorHex;

    private String categoryId;

    private Set<@NotBlank(message = "Photo URL cannot be blank.") String> photoUrls;

}
