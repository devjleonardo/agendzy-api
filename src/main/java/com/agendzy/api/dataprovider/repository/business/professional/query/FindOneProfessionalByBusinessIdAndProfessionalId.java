package com.agendzy.api.dataprovider.repository.business.professional.query;

import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.business.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@RequiredArgsConstructor
public class FindOneProfessionalByBusinessIdAndProfessionalId
        implements FindOneGateway<Professional, WhereBusinessIdAndEntityId> {

    private final ProfessionalRepository repository;

    @Override
    public OutputResponse<Professional> execute(WhereBusinessIdAndEntityId where) {
        return repository.findByBusinessIdAndProfessionalId(where.businessId(), where.entityId())
                .map(OutputResponseFactory::success)
                .orElseGet(() -> error(entityNotFound("Professional not found.")));
    }
}
