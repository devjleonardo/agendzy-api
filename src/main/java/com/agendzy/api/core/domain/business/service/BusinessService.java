package com.agendzy.api.core.domain.business.service;

import com.agendzy.api.core.domain.business.BusinessTenantEntity;
import com.agendzy.api.util.DurationAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class BusinessService extends BusinessTenantEntity {

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Convert(converter = DurationAttributeConverter.class)
    @Column(nullable = false)
    private Duration duration;

    @Column(nullable = false)
    private Double price;

    private Double promotionalPrice;

    @Column(length = 10)
    private String colorHex;

    @ManyToOne
    @JoinColumn
    private BusinessServiceCategory category;

    @ElementCollection
    @CollectionTable(name = "business_service_photos", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "photo_url")
    private Set<String> photoUrls = new HashSet<>();

    @Override
    public boolean equals(Object otherBusinessService) {
        return super.equals(otherBusinessService);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
