package com.agendzy.api.core.domain.business.collaborator;

import com.agendzy.api.core.domain.business.BusinessTenantEntity;
import com.agendzy.api.core.domain.common.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "business_collaborator")
@Getter
@Setter
public class Collaborator extends BusinessTenantEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String nickname;

    private String inviteEmail;

    @Enumerated(EnumType.STRING)
    private CollaboratorInviteStatus inviteStatus;

    @Enumerated(EnumType.STRING)
    private CollaboratorRole role;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 300)
    private String photoUrl;

    private boolean active = true;

    @ManyToOne(optional = false)
    @JoinColumn
    private User user;

    @Override
    public boolean equals(Object otherCollaborator) {
        return super.equals(otherCollaborator);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
