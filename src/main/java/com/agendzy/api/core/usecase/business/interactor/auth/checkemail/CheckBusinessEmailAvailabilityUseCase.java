package com.agendzy.api.core.usecase.business.interactor.auth.checkemail;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.data.auth.checkemail.BusinessEmailAvailabilityInput;
import com.agendzy.api.core.usecase.business.boundary.output.auth.BusinessEmailAvailabilityOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Service
@RequiredArgsConstructor
public class CheckBusinessEmailAvailabilityUseCase {

    private final FindOneGateway<Business, WhereEmail> findBusinessByOwnerEmail;

    public OutputResponse<BusinessEmailAvailabilityOutput> execute(BusinessEmailAvailabilityInput input) {
        boolean businessEmailAlreadyInUse = isEmailAlreadyOwnerOfBusiness(input);
        return success(new BusinessEmailAvailabilityOutput(businessEmailAlreadyInUse));
    }

    private boolean isEmailAlreadyOwnerOfBusiness(BusinessEmailAvailabilityInput input) {
        return findBusinessByOwnerEmail.execute(new WhereEmail(input.getEmail())).isSuccess();
    }

}
