package com.agendzy.api.core.domain.business;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.business.openinghours.BusinessOpeningHours;
import com.agendzy.api.core.domain.business.phone.BusinessPhone;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Business extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20)
    private String cpfCnpj;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BusinessSegment segment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BusinessTeamSize teamSize;

    @Embedded
    private BusinessSocialLinks socialLinks;

    @Embedded
    private BusinessLocation location;

    @OneToOne
    @JoinColumn
    private Collaborator owner;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessPhone> phones = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusinessService> services = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private Set<BusinessOpeningHours> openingHours = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private Set<Collaborator> collaborators = new HashSet<>();

    public void addPhone(BusinessPhone phone) {
        phones.add(phone);
    }

    public void addService(BusinessService service) {
        services.add(service);
    }

    @Override
    public boolean equals(Object otherBusiness) {
        return super.equals(otherBusiness);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
