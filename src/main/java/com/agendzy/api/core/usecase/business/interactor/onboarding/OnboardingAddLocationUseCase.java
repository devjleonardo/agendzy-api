package com.agendzy.api.core.usecase.business.interactor.onboarding;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.mapper.business.BusinessLocationMapper;
import com.agendzy.api.core.usecase.business.boundary.input.data.location.BusinessLocationInput;
import com.agendzy.api.core.usecase.business.boundary.output.location.BusinessLocationOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@RequiredArgsConstructor
@Service
public class OnboardingAddLocationUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final SaveGateway<Business> businessSave;
    private final BusinessLocationMapper locationMapper;

    public OutputResponse<BusinessLocationOutput> execute(String businessId, BusinessLocationInput input) {
        Business business = getAuthorizedBusiness(businessId);
        business.setLocation(locationMapper.toEntity(input));
        businessSave.execute(business);

        var location = locationMapper.toOutput(business.getLocation());

        return success(location);
    }

    private Business getAuthorizedBusiness(String businessId) {
        return checkAccessAndGetBusiness(businessId, findBusinessById).getData();
    }

}

