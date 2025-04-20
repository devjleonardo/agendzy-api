package com.agendzy.api.dataprovider.repository.business.collaborator.query;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndUserId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.business.collaborator.CollaboratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;

@Component
@RequiredArgsConstructor
public class FindOneCollaboratorByBusinessIdAndUserId
        implements FindOneGateway<Collaborator, WhereBusinessIdAndUserId> {

    private final CollaboratorRepository repository;

    @Override
    public OutputResponse<Collaborator> execute(WhereBusinessIdAndUserId where) {
        return repository
            .findByUserIdAndBusinessId(where.businessId(), where.userId())
            .map(OutputResponseFactory::success)
            .orElseGet(() -> OutputResponseFactory.error(
                entityNotFound("No collaborator found for the specified user and business.")
            ));
    }

}
