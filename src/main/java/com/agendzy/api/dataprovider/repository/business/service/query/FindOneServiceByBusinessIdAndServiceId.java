package com.agendzy.api.dataprovider.repository.business.service.query;

import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.business.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@RequiredArgsConstructor
public class FindOneServiceByBusinessIdAndServiceId implements
    FindOneGateway<BusinessService, WhereBusinessIdAndEntityId> {

    private final ServiceRepository repository;

    @Override
    public OutputResponse<BusinessService> execute(WhereBusinessIdAndEntityId where) {
        return repository.findByBusinessIdAndServiceId(where.businessId(), where.entityId())
            .map(OutputResponseFactory::success)
            .orElseGet(() -> error(entityNotFound("Service not found.")));
    }

}
