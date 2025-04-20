package com.agendzy.api.core.usecase.business.interactor.service;

import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.mapper.business.ServiceMapper;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessId;
import com.agendzy.api.core.usecase.business.boundary.output.service.ServiceOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class ListServicesUseCase {

    private final FindAllWithFilterGateway<BusinessService, WhereBusinessId> findAllServicesByBusinessId;
    private final ServiceMapper serviceMapper;

    public OutputResponse<List<ServiceOutput>> execute(String businessId) {
        checkAccessToBusiness(businessId);

        var servicesResponse = findAllServicesByBusinessId.execute(new WhereBusinessId(businessId));

        if (servicesResponse.isError()) {
            return error(servicesResponse.getErrors());
        }

        List<ServiceOutput> outputs = servicesResponse.getData().stream()
                .map(serviceMapper::toOutput)
                .toList();

        return success(outputs);
    }

}
