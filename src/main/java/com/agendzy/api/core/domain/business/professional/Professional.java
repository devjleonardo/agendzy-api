package com.agendzy.api.core.domain.business.professional;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.business.service.BusinessService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("PROFESSIONAL")
@Getter
@Setter
public class Professional extends Collaborator {

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfessionalWorkSchedule> workSchedules = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "business_professional_services",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<BusinessService> services = new HashSet<>();

    @Override
    public boolean equals(Object otherCollaborator) {
        return super.equals(otherCollaborator);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
}
