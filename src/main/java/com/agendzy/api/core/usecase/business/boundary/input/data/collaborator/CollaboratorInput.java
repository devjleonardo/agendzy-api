package com.agendzy.api.core.usecase.business.boundary.input.data.collaborator;

import com.agendzy.api.core.domain.business.collaborator.CollaboratorRole;
import com.agendzy.api.core.usecase.business.boundary.input.data.professional.ProfessionalWorkScheduleInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CollaboratorInput {

    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String name;

    @Size(max = 100, message = "Nickname must be at most 100 characters.")
    private String nickname;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Size(max = 120, message = "Email must be at most 120 characters.")
    private String inviteEmail;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 20, message = "Phone number must be at most 20 characters.")
    private String phoneNumber;

    @NotNull(message = "Role is required.")
    private CollaboratorRole role;

    @Size(max = 300, message = "Photo URL must be at most 300 characters.")
    private String photoUrl;

    // campos opcionais se role for PROFESSIONAL
    private Set<@NotBlank(message = "Service ID cannot be blank.") String> serviceIds;

    private List<ProfessionalWorkScheduleInput> workSchedules;

}
