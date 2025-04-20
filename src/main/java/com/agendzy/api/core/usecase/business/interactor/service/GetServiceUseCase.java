package com.agendzy.api.core.usecase.business.interactor.service;

import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.mapper.business.ServiceMapper;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.business.boundary.output.service.ServiceOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class GetServiceUseCase {

    private final FindOneGateway<BusinessService, WhereBusinessIdAndEntityId> findServiceByBusinessAndId;
    private final ServiceMapper serviceMapper;

    public OutputResponse<ServiceOutput> execute(String businessId, String categoryId) {
        checkAccessToBusiness(businessId);

        var serviceResponse = findServiceByBusinessAndId.execute(new WhereBusinessIdAndEntityId(businessId, categoryId));

        if (serviceResponse.isError()) {
            return error(serviceResponse.getErrors());
        }

        return success(serviceMapper.toOutput(serviceResponse.getData()));
    }

}
