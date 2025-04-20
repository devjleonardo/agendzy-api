package com.agendzy.api.core.domain.business.phone;

import com.agendzy.api.core.domain.business.BusinessTenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BusinessPhone extends BusinessTenantEntity {

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private PhoneType type;

    @Override
    public boolean equals(Object otherPhoneBusiness) {
        return super.equals(otherPhoneBusiness);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
