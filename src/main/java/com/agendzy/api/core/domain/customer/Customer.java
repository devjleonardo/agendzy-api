package com.agendzy.api.core.domain.customer;

import com.agendzy.api.core.domain.common.BaseEntity;
import com.agendzy.api.core.domain.common.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer extends BaseEntity {

    private String profilePhotoUrl;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String location;

    private boolean marketingOptIn; // Aceita marketing

    private String referralCode; // Código de indicação

    private String notes; // Notas adicionais

    @ElementCollection
    private Set<String> preferredServices; // Serviços preferidos

    @ElementCollection
    private Set<String> allergiesOrRestrictions; // Alergias/restrições

    @OneToOne(optional = false)
    private User user;

    @Override
    public boolean equals(Object otherCustomer) {
        return super.equals(otherCustomer);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
