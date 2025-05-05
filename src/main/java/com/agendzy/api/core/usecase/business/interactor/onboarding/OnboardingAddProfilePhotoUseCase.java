package com.agendzy.api.core.usecase.business.interactor.onboarding;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.business.boundary.input.data.profile.BusinessProfileInput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@Service
@RequiredArgsConstructor
public class OnboardingAddProfilePhotoUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final SaveGateway<Business> businessSave;

    public OutputResponse<String > execute(String businessId, BusinessProfileInput input) {
        Business business = checkAccessAndGetBusiness(businessId, findBusinessById).getData();
        business.setProfilePhotoUrl(input.getProfilePhotoUrl());
        businessSave.execute(business);
        return success(business.getProfilePhotoUrl());
    }

}
