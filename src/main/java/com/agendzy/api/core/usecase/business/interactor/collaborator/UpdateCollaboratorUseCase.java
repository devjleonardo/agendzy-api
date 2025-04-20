package com.agendzy.api.core.usecase.business.interactor.collaborator;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.business.collaborator.CollaboratorInviteStatus;
import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.domain.business.professional.ProfessionalWorkSchedule;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.business.boundary.input.data.collaborator.CollaboratorInput;
import com.agendzy.api.core.usecase.business.boundary.input.data.professional.ProfessionalWorkScheduleInput;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndInviteEmail;
import com.agendzy.api.core.usecase.business.boundary.output.collaborator.GetOneCollaboratorOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.*;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class UpdateCollaboratorUseCase {

    private final FindOneGateway<Collaborator, WhereBusinessIdAndEntityId> findCollaboratorByBusinessAndId;
    private final FindOneGateway<BusinessService, WhereBusinessIdAndEntityId> findServiceByBusinessAndId;
    private final SaveGateway<Collaborator> saveCollaborator;
    private final FindOneGateway<Collaborator, WhereBusinessIdAndInviteEmail> findCollaboratorByInviteEmailInBusiness;

    public OutputResponse<GetOneCollaboratorOutput> execute(String businessId,
                                                            String collaboratorId,
                                                            CollaboratorInput input) {
        checkAccessToBusiness(businessId);

        var collaboratorResponse = findCollaboratorByBusinessAndId
            .execute(new WhereBusinessIdAndEntityId(businessId, collaboratorId));

        if (collaboratorResponse.isError()) {
            return error(collaboratorResponse.getErrors());
        }

        Collaborator collaborator = collaboratorResponse.getData();

        var existingCollaborator = findCollaboratorByInviteEmailInBusiness.execute(
            new WhereBusinessIdAndInviteEmail(businessId, input.getInviteEmail()));

        if (existingCollaborator.isSuccess() && !existingCollaborator.getData().getId().equals(collaborator.getId())) {
            return errorBusinessRule("A collaborator with this email already exists in this business.");
        }

        collaborator.setName(input.getName());
        collaborator.setNickname(input.getNickname());
        collaborator.setPhoneNumber(input.getPhoneNumber());
        collaborator.setPhotoUrl(input.getPhotoUrl());
        collaborator.setRole(input.getRole());

        boolean canChangeEmail = collaborator.getInviteStatus() != CollaboratorInviteStatus.ACCEPT
            || collaborator.getInviteEmail().equals(input.getInviteEmail());

        if (canChangeEmail) {
            collaborator.setInviteEmail(input.getInviteEmail());
        }

        if (collaborator instanceof Professional professional) {
            var result = updateProfessionalData(professional, input, businessId);

            if (result.isError()) {
                return error(result.getErrors());
            }
        }

        var saved = saveCollaborator.execute(collaborator);

        return success(GetOneCollaboratorOutput.of(saved.getData()));
    }

    private OutputResponse<Void> updateProfessionalData(Professional professional,
                                                        CollaboratorInput input,
                                                        String businessId) {
        if (input.getServiceIds() != null) {
            var servicesResult = resolveProfessionalServices(input.getServiceIds(), businessId);

            if (servicesResult.isError()) {
                return error(servicesResult.getErrors());
            }
            professional.setServices(servicesResult.getData());
        }

        if (input.getWorkSchedules() != null) {
            syncWorkSchedules(professional, input.getWorkSchedules());
        }

        return success();
    }

    private OutputResponse<Set<BusinessService>> resolveProfessionalServices(Set<String> serviceIds, String businessId) {
        Set<BusinessService> services = new HashSet<>();

        for (String serviceId : serviceIds) {
            var response = findServiceByBusinessAndId.execute(new WhereBusinessIdAndEntityId(serviceId, businessId));

            if (response.isError()) {
                return error(response.getErrors());
            }
            services.add(response.getData());
        }

        return success(services);
    }

    private void syncWorkSchedules(Professional professional, List<ProfessionalWorkScheduleInput> scheduleInputs) {
        Set<ProfessionalWorkSchedule> currentSchedules = professional.getWorkSchedules();

        Map<String, ProfessionalWorkSchedule> currentById = currentSchedules.stream()
            .filter(ws -> ws.getId() != null)
            .collect(Collectors.toMap(ProfessionalWorkSchedule::getId, ws -> ws));

        Set<ProfessionalWorkSchedule> updatedSchedules = new HashSet<>();

        for (var input : scheduleInputs) {
            ProfessionalWorkSchedule schedule = (input.getId() != null && currentById.containsKey(input.getId()))
                ? currentById.get(input.getId())
                : createNewSchedule(professional);

            schedule.setDayOfWeek(input.getDayOfWeek());
            schedule.setStartTime(input.getStartTime());
            schedule.setEndTime(input.getEndTime());
            schedule.setEnabled(input.isEnabled());

            updatedSchedules.add(schedule);
        }

        currentSchedules.clear();
        currentSchedules.addAll(updatedSchedules);
    }

    private ProfessionalWorkSchedule createNewSchedule(Professional professional) {
        ProfessionalWorkSchedule schedule = new ProfessionalWorkSchedule();
        schedule.setProfessional(professional);
        schedule.setBusiness(professional.getBusiness());
        return schedule;
    }

}
