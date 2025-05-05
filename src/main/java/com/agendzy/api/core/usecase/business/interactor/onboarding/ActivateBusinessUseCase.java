package com.agendzy.api.core.usecase.business.interactor.onboarding;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.*;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@RequiredArgsConstructor
@Service
public class ActivateBusinessUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final SaveGateway<Business> businessSave;

    public OutputResponse<Void> execute(String businessId) {
        Business business = getAuthorizedBusiness(businessId);

        List<String> missingSteps = getMissingSteps(business);

        if (!missingSteps.isEmpty()) {
            return errorBusinessRule(buildMissingStepsMessage(missingSteps));
        }

        business.setActive(true);
        businessSave.execute(business);

        return success();
    }

    private Business getAuthorizedBusiness(String businessId) {
        return checkAccessAndGetBusiness(businessId, findBusinessById).getData();
    }

    private List<String> getMissingSteps(Business business) {
        List<String> missing = new ArrayList<>();

        if (business.getServices() == null || business.getServices().isEmpty()) {
            missing.add("services");
        }
        if (business.getOpeningHours() == null || business.getOpeningHours().isEmpty()) {
            missing.add("opening hours");
        }
        if (business.getLocation() == null) {
            missing.add("location");
        }
        if (business.getProfilePhotoUrl() == null || business.getProfilePhotoUrl().isBlank()) {
            missing.add("profile photo");
        }

        return missing;
    }

    private String buildMissingStepsMessage(List<String> missingSteps) {
        return "Business cannot be activated. Missing steps: " + String.join(", ", missingSteps) + ".";
    }

}
