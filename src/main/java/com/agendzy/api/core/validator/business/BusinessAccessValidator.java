package com.agendzy.api.core.validator.business;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.auth.CollaboratorContextCurrent;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.DomainError;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.unauthorized;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

public class BusinessAccessValidator {

    private BusinessAccessValidator() {}

    public static OutputResponse<Business> checkAccessAndGetBusiness(String businessId,
                                                                     FindOneGateway<Business, WhereId> findBusinessById) {
        return checkAccessToBusiness(businessId)
                .flatMap(ignore -> findBusinessById.execute(new WhereId(businessId)));
    }

    public static OutputResponse<Void> checkAccessToBusiness(String businessId) {
        if (!businessId.equals(CollaboratorContextCurrent.getBusinessId())) {
            return error(unauthorized(DomainError.BUSINESS_ACCESS_DENIED));
        }
        return success();
    }

}
