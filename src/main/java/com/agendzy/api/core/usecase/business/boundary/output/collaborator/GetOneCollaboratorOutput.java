package com.agendzy.api.core.usecase.business.boundary.output.collaborator;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.domain.business.professional.ProfessionalWorkSchedule;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOneCollaboratorOutput {

    private String id;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String photoUrl;
    private String role;
    private boolean active;
    private String email;

    private Set<ServiceOutput> services;
    private List<WorkScheduleOutput> workSchedules;

    public static GetOneCollaboratorOutput of(Collaborator collaborator) {
        var builder = GetOneCollaboratorOutput.builder()
            .id(collaborator.getId())
            .name(collaborator.getName())
            .nickname(collaborator.getNickname())
            .phoneNumber(collaborator.getPhoneNumber())
            .photoUrl(collaborator.getPhotoUrl())
            .role(collaborator.getRole().name())
            .active(collaborator.isActive())
            .email(collaborator.getUser().getEmail());

        if (collaborator instanceof Professional professional) {
            builder.services(professional.getServices()
                .stream()
                .map(ServiceOutput::of)
                .collect(Collectors.toSet()));

            builder.workSchedules(professional.getWorkSchedules()
                .stream()
                .sorted(Comparator.comparing(ws -> ws.getDayOfWeek().getValue()))
                .map(WorkScheduleOutput::of)
                .toList());
        }

        return builder.build();
    }

    @Getter
    @Builder
    public static class ServiceOutput {
        private String id;
        private String name;

        public static ServiceOutput of(BusinessService service) {
            return ServiceOutput.builder()
                .id(service.getId())
                .name(service.getName())
                .build();
        }
    }

    @Getter
    @Builder
    public static class WorkScheduleOutput {
        private String id;
        private String dayOfWeek;
        private String startTime;
        private String endTime;
        private boolean enabled;

        public static WorkScheduleOutput of(ProfessionalWorkSchedule ws) {
            return WorkScheduleOutput.builder()
                .id(ws.getId())
                .dayOfWeek(ws.getDayOfWeek().name())
                .startTime(ws.getStartTime().toString())
                .endTime(ws.getEndTime().toString())
                .enabled(ws.isEnabled())
                .build();
        }
    }

}
