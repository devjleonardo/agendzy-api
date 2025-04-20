package com.agendzy.api.core.domain.business.professional;

import com.agendzy.api.core.domain.business.BusinessTenantEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "business_professional_work_schedule")
@Getter
@Setter
public class ProfessionalWorkSchedule extends BusinessTenantEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Professional professional;

    @Override
    public boolean equals(Object otherProfessionalWorkSchedule) {
        return super.equals(otherProfessionalWorkSchedule);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
