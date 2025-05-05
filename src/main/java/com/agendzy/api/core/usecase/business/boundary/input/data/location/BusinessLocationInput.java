package com.agendzy.api.core.usecase.business.boundary.input.data.location;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessLocationInput {

    @NotBlank(message = "Address is required.")
    @Size(max = 100, message = "Address must be at most 100 characters.")
    private String address;

    @NotBlank(message = "Zipcode is required.")
    @Size(max = 20, message = "Zipcode must be at most 20 characters.")
    private String zipcode;

    @NotBlank(message = "City is required.")
    @Size(max = 100, message = "City must be at most 100 characters.")
    private String city;

    @NotBlank(message = "State is required.")
    @Size(max = 50, message = "State must be at most 50 characters.")
    private String state;

    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90.0.")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90.0.")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be >= -180.0.")
    @DecimalMax(value = "180.0", message = "Longitude must be <= 180.0.")
    private Double longitude;

    @Size(max = 50, message = "Time zone name must be at most 50 characters.")
    private String timeZoneName;

}
