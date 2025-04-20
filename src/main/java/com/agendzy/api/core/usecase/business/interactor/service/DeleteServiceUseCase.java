package com.agendzy.api.core.usecase.business.interactor.service;

import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.gateway.common.DeleteGateway;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class DeleteServiceUseCase {

    private final FindOneGateway<BusinessService, WhereBusinessIdAndEntityId> findCategoryByBusinessAndId;
    private final DeleteGateway<BusinessService> deleteService;

    public OutputResponse<Void> execute(String businessId, String categoryId) {
        checkAccessToBusiness(businessId);

        var serviceResponse = findCategoryByBusinessAndId.execute(new WhereBusinessIdAndEntityId(businessId, categoryId));

        if (serviceResponse.isError()) {
            return error(serviceResponse.getErrors());
        }

        deleteService.execute(serviceResponse.getData());

        return success();
    }

}
