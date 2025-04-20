package com.agendzy.api.core.usecase.business.boundary.output.auth;
import com.agendzy.api.core.domain.business.collaborator.CollaboratorRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthCollaboratorTokenDataOutput {

    private String collaboratorId;
    private String email;
    private CollaboratorRole role;

    private String businessId;

    private Boolean tokenValid;
    private Boolean tokenExpired;

    public static AuthCollaboratorTokenDataOutput empty() {
        return AuthCollaboratorTokenDataOutput.builder().build();
    }

}
