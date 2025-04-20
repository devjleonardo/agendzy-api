package com.agendzy.api.core.domain.business.service;

import com.agendzy.api.core.domain.business.BusinessTenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BusinessServiceCategory extends BusinessTenantEntity {

    @Column(nullable = false, length = 100)
    private String name;

    private boolean active = true;

    @Override
    public boolean equals(Object otherBusinessServiceCategory) {
        return super.equals(otherBusinessServiceCategory);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
