package com.agendzy.api.core.usecase.business.interactor.onboarding;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.mapper.business.OpeningHoursMapper;
import com.agendzy.api.core.usecase.business.boundary.input.data.openinghours.BusinessOpeningHoursInput;
import com.agendzy.api.core.usecase.business.boundary.output.openinghours.OpeningHoursOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@RequiredArgsConstructor
@Service
public class OnboardingAddOpeningHoursUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final SaveGateway<Business> businessSave;
    private final OpeningHoursMapper openingHoursMapper;

    public OutputResponse<List<OpeningHoursOutput>> execute(String businessId, List<BusinessOpeningHoursInput> inputs) {
        Business business = getAuthorizedBusiness(businessId);

        inputs.stream()
            .filter(this::isValid)
            .map(openingHoursMapper::toEntity)
            .forEach(business::addOpeningHour);

        Business savedBusiness = businessSave.execute(business).getData();

        var openingHours = openingHoursMapper.toOutputList(savedBusiness.getOpeningHours());

        return success(openingHours);
    }

    private Business getAuthorizedBusiness(String businessId) {
        return checkAccessAndGetBusiness(businessId, findBusinessById).getData();
    }

    private boolean isValid(BusinessOpeningHoursInput input) {
        return input.getDayOfWeek() != null
            && input.getStartTime() != null
            && input.getEndTime() != null;
    }

}
