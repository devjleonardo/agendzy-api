package com.agendzy.api.core.domain.business.openinghours;

import com.agendzy.api.core.domain.business.BusinessTenantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class BusinessOpeningHours extends BusinessTenantEntity {

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean enabled = true;

    @Override
    public boolean equals(Object otherOpeningHours) {
        return super.equals(otherOpeningHours);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
