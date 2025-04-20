package com.agendzy.api.dataprovider.repository.business.service.category.query;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.business.service.category.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@RequiredArgsConstructor
public class FindOneServiceCategoryByBusinessIdAndCategoryId implements
    FindOneGateway<BusinessServiceCategory, WhereBusinessIdAndEntityId> {

    private final ServiceCategoryRepository repository;

    @Override
    public OutputResponse<BusinessServiceCategory> execute(WhereBusinessIdAndEntityId where) {
        return repository.findByBusinessIdAndCategoryId(where.businessId(), where.entityId())
            .map(OutputResponseFactory::success)
            .orElseGet(() -> error(entityNotFound("Service category not found.")));
    }

}
