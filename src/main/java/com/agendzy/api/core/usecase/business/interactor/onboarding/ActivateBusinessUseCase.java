package com.agendzy.api.core.usecase.business.interactor.onboarding;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@RequiredArgsConstructor
@Service
public class ActivateBusinessUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final SaveGateway<Business> businessSave;

    public OutputResponse<Void> execute(String businessId) {
        Business business = checkAccessAndGetBusiness(businessId, findBusinessById).getData();

        boolean isComplete =
                !business.getServices().isEmpty()
                && !business.getOpeningHours().isEmpty()
                && business.getLocation() != null
                && business.getProfilePhotoUrl() != null && !business.getProfilePhotoUrl().isBlank();

        if (isComplete) {
            business.setActive(true);
            businessSave.execute(business);
        }

        return success();
    }

}
