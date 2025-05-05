package com.agendzy.api.core.usecase.business.boundary.output.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessLocationOutput {

    private String address;

    private String zipcode;

    private String city;

    private String state;

    private Double latitude;

    private Double longitude;

    private String timeZoneName;

}
