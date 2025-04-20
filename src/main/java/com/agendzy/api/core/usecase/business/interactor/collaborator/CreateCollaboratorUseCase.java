package com.agendzy.api.core.usecase.business.interactor.collaborator;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.business.collaborator.CollaboratorInviteStatus;
import com.agendzy.api.core.domain.business.collaborator.CollaboratorRole;
import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.domain.business.professional.ProfessionalWorkSchedule;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.business.boundary.input.data.collaborator.CollaboratorInput;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndInviteEmail;
import com.agendzy.api.core.usecase.business.boundary.output.collaborator.GetOneCollaboratorOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.*;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@Service
@RequiredArgsConstructor
public class CreateCollaboratorUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final FindOneGateway<BusinessService, WhereBusinessIdAndEntityId> findServiceById;
    private final FindOneGateway<User, WhereEmail> findUserByEmail;
    private final SaveGateway<User> saveUser;
    private final SaveGateway<Collaborator> saveCollaborator;
    private final FindOneGateway<Collaborator, WhereBusinessIdAndInviteEmail> findCollaboratorByInviteEmailInBusiness;

    public OutputResponse<GetOneCollaboratorOutput> execute(String businessId, CollaboratorInput input) {
        Business business = checkAccessAndGetBusiness(businessId, findBusinessById).getData();

        var existingCollaborator = findCollaboratorByInviteEmailInBusiness.execute(
            new WhereBusinessIdAndInviteEmail(businessId, input.getInviteEmail()));

        if (existingCollaborator.isSuccess()) {
            return errorBusinessRule("A collaborator with this email already exists in this business.");
        }

        var collaborator = buildCollaborator(input, business);

        if (collaborator instanceof Professional professional) {
            var result = populateProfessionalData(professional, input, business, businessId);

            if (result.isError()) {
                return error(result.getErrors());
            }
        }

        var savedCollaborator = saveCollaborator.execute(collaborator).getData();

        return success(GetOneCollaboratorOutput.of(savedCollaborator));
    }

    private Collaborator buildCollaborator(CollaboratorInput input, Business business) {
        Collaborator collaborator = input.getRole() == CollaboratorRole.PROFESSIONAL
                ? new Professional()
                : new Collaborator();

        User user = getOrCreateUser(input.getInviteEmail());

        collaborator.setName(input.getName());
        collaborator.setNickname(input.getNickname());
        collaborator.setPhoneNumber(input.getPhoneNumber());
        collaborator.setPhotoUrl(input.getPhotoUrl());
        collaborator.setRole(input.getRole());
        collaborator.setInviteEmail(input.getInviteEmail());
        collaborator.setInviteStatus(CollaboratorInviteStatus.PENDING);
        collaborator.setUser(user);
        collaborator.setBusiness(business);

        return collaborator;
    }

    private User getOrCreateUser(String email) {
        var userResponse = findUserByEmail.execute(new WhereEmail(email));

        if (userResponse.isSuccess()) {
            return userResponse.getData();
        } else {
            User user = new User();
            user.setEmail(email);
            user.setCreationCompleted(false);
            return saveUser.execute(user).getData();
        }
    }

    private OutputResponse<Void> populateProfessionalData(Professional professional,
                                                          CollaboratorInput input,
                                                          Business business,
                                                          String businessId) {
        if (input.getServiceIds() != null) {
            Set<BusinessService> services = new HashSet<>();
            for (String serviceId : input.getServiceIds()) {
                var serviceResponse = findServiceById.execute(new WhereBusinessIdAndEntityId(serviceId, businessId));

                if (serviceResponse.isError()) {
                    return error(serviceResponse.getErrors());
                }
                services.add(serviceResponse.getData());
            }
            professional.setServices(services);
        }

        if (input.getWorkSchedules() != null) {
            Set<ProfessionalWorkSchedule> schedules = input.getWorkSchedules().stream()
                    .map(wsInput -> {
                        ProfessionalWorkSchedule schedule = new ProfessionalWorkSchedule();
                        schedule.setDayOfWeek(wsInput.getDayOfWeek());
                        schedule.setStartTime(wsInput.getStartTime());
                        schedule.setEndTime(wsInput.getEndTime());
                        schedule.setEnabled(wsInput.isEnabled());
                        schedule.setProfessional(professional);
                        schedule.setBusiness(business);
                        return schedule;
                    }).collect(Collectors.toSet());

            professional.setWorkSchedules(schedules);
        }

        return success();
    }

}
