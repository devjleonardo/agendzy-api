package com.agendzy.api.core.domain.business;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BusinessSocialLinks {

    private String website;

    private String facebook;

    private String instagram;

}
