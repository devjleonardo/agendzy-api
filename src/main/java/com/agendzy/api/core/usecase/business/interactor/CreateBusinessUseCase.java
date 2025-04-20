package com.agendzy.api.core.usecase.business.interactor;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.business.collaborator.CollaboratorRole;
import com.agendzy.api.core.domain.business.openinghours.BusinessOpeningHours;
import com.agendzy.api.core.domain.business.phone.BusinessPhone;
import com.agendzy.api.core.domain.business.phone.PhoneType;
import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.business.boundary.input.data.BusinessOpeningHoursInput;
import com.agendzy.api.core.usecase.business.boundary.input.data.CreateBusinessInput;
import com.agendzy.api.core.usecase.business.boundary.output.BusinessCreatedOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.interactor.CreateUserUseCase;
import com.agendzy.api.util.LogUtil;
import com.agendzy.api.util.TimeMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Service
@RequiredArgsConstructor
public class CreateBusinessUseCase {

    private final CreateUserUseCase createUser;
    private final SaveGateway<Business> businessSave;
    private final SaveGateway<Collaborator> collaboratorSave;

    @Transactional
    public OutputResponse<BusinessCreatedOutput> execute(CreateBusinessInput input) {
        try {
            User savedUser = createUser.execute(input.getUser());
            Business savedBusiness = saveBusinessAndOwner(input, savedUser);
            return success(BusinessCreatedOutput.of(savedBusiness));
        } catch (Exception e) {
            LogUtil.logError("Unexpected error while creating business.", e);
            throw e;
        }
    }

    private Business saveBusinessAndOwner(CreateBusinessInput input, User user) {
        Business business = buildBusiness(input, user);
        Business savedBusiness = businessSave.execute(business).getData();

        Collaborator owner = buildOwnerCollaborator(user, savedBusiness);
        Collaborator savedOwner = collaboratorSave.execute(owner).getData();

        savedBusiness.setOwner(savedOwner);

        return businessSave.execute(savedBusiness).getData();
    }

    private Business buildBusiness(CreateBusinessInput input, User ownerUser) {
        Business business = new Business();
        business.setName(input.getName());
        business.setSegment(input.getSegment());
        business.setLocation(input.getLocation());

        if (input.getOpeningHours() != null) {
            business.setOpeningHours(mapOpeningHours(input.getOpeningHours(), business));
        }

        if (ownerUser.getPhoneNumber() != null && !ownerUser.getPhoneNumber().isBlank()) {
            business.addPhone(buildOwnerPhone(ownerUser, business));
        }

        return business;
    }

    private BusinessPhone buildOwnerPhone(User owner, Business business) {
        BusinessPhone phone = new BusinessPhone();
        phone.setPhoneNumber(owner.getPhoneNumber());
        phone.setType(PhoneType.WHATSAPP);
        phone.setBusiness(business);
        return phone;
    }

    private Collaborator buildOwnerCollaborator(User user, Business business) {
        Professional collaborator = new Professional();
        collaborator.setName(user.getFullName());
        collaborator.setNickname(extractNickname(user.getFullName()));
        collaborator.setRole(CollaboratorRole.OWNER);
        collaborator.setUser(user);
        collaborator.setBusiness(business);
        return collaborator;
    }

    private Set<BusinessOpeningHours> mapOpeningHours(List<BusinessOpeningHoursInput> inputs, Business business) {
        return inputs.stream()
            .map(input -> {
                BusinessOpeningHours hour = new BusinessOpeningHours();
                hour.setBusiness(business);
                hour.setDayOfWeek(TimeMappingUtil.toDayOfWeek(input.getDayOfWeek()));
                hour.setStartTime(TimeMappingUtil.toLocalTime(input.getStartTime()));
                hour.setEndTime(TimeMappingUtil.toLocalTime(input.getEndTime()));
                hour.setEnabled(input.isEnabled());
                return hour;
            })
            .collect(Collectors.toSet());
    }

    private String extractNickname(String fullName) {
        return fullName.contains(" ") ? fullName.split(" ")[0] : fullName;
    }

}
