package com.agendzy.api.dataprovider.repository.business.collaborator.query;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.business.collaborator.CollaboratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@RequiredArgsConstructor
public class FindOneCollaboratorByBusinessIdAndCollaboratorId
        implements FindOneGateway<Collaborator, WhereBusinessIdAndEntityId> {

    private final CollaboratorRepository repository;

    @Override
    public OutputResponse<Collaborator> execute(WhereBusinessIdAndEntityId where) {
        return repository.findByBusinessIdAndCollaboratorId(where.businessId(), where.entityId())
            .map(OutputResponseFactory::success)
            .orElseGet(() -> error(entityNotFound("Collaborator not found.")));
    }

}
