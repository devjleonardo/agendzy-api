package com.agendzy.api.core.domain.business.auth;

import com.agendzy.api.core.domain.business.collaborator.CollaboratorRole;
import com.agendzy.api.core.usecase.business.boundary.output.auth.AuthCollaboratorTokenDataOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
public final class CollaboratorContext {

    private final String collaboratorId;
    private final String email;
    private final String businessId;
    private final CollaboratorRole role;

    public CollaboratorContext(AuthCollaboratorTokenDataOutput data) {
        this.collaboratorId = data.getCollaboratorId();
        this.email = data.getEmail();
        this.businessId = data.getBusinessId();
        this.role = data.getRole();
    }

}
