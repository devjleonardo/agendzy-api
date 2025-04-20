package com.agendzy.api.core.usecase.business.boundary.output.collaborator;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollaboratorOutput {

    private String id;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String photoUrl;
    private String role;
    private boolean active;
    private String email;

    public static CollaboratorOutput of(Collaborator collaborator) {
        return CollaboratorOutput.builder()
                .id(collaborator.getId())
                .name(collaborator.getName())
                .nickname(collaborator.getNickname())
                .phoneNumber(collaborator.getPhoneNumber())
                .photoUrl(collaborator.getPhotoUrl())
                .role(collaborator.getRole().name())
                .active(collaborator.isActive())
                .email(collaborator.getUser().getEmail())
                .build();
    }

}
