package com.agendzy.api.dataprovider.repository.business.query;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.business.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@RequiredArgsConstructor
public class FindOneBusinessById implements FindOneGateway<Business, WhereId> {

    private final BusinessRepository repository;

    @Override
    public OutputResponse<Business> execute(WhereId where) {
        return repository.findById(where.id())
                .map(OutputResponseFactory::success)
                .orElseGet(() -> error(entityNotFound("Business not found for the specified ID.")));
    }

}
