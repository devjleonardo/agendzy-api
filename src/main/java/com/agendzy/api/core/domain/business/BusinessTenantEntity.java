package com.agendzy.api.core.domain.business;

import com.agendzy.api.core.domain.common.BaseEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BusinessTenantEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Override
    public boolean equals(Object otherBusinessTenantEntity) {
        return super.equals(otherBusinessTenantEntity);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
