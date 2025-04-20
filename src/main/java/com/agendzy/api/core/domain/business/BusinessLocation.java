package com.agendzy.api.core.domain.business;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BusinessLocation {

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 20)
    private String zipcode;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 50)
    private String state;

    private Double latitude;

    private Double longitude;

    @Column(length = 50)
    private String timeZoneName;

}
