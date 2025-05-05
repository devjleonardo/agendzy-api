package com.agendzy.api.core.usecase.business.interactor.onboarding;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.output.onboarding.OnboardingStatusOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@RequiredArgsConstructor
@Service
public class GetOnboardingStatusUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;

    public OutputResponse<OnboardingStatusOutput> execute(String businessId) {
        Business business = checkAccessAndGetBusiness(businessId, findBusinessById).getData();

        boolean hasServices = !business.getServices().isEmpty();
        boolean hasHours = !business.getOpeningHours().isEmpty();
        boolean hasLocation = business.getLocation() != null;
        boolean hasPhoto = business.getProfilePhotoUrl() != null && !business.getProfilePhotoUrl().isBlank();

        return success(OnboardingStatusOutput.builder()
                .hasAtLeastOneService(hasServices)
                .hasOpeningHours(hasHours)
                .hasLocation(hasLocation)
                .hasProfilePhoto(hasPhoto)
                .isReady(hasServices && hasHours && hasLocation && hasPhoto)
                .build());
    }

}
