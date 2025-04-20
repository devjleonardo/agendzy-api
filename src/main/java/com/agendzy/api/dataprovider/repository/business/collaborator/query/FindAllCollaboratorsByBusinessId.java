package com.agendzy.api.dataprovider.repository.business.collaborator.query;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.dataprovider.repository.business.collaborator.CollaboratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class FindAllCollaboratorsByBusinessId implements FindAllWithFilterGateway<Collaborator, WhereBusinessId> {

    private final CollaboratorRepository repository;

    @Override
    public OutputResponse<List<Collaborator>> execute(WhereBusinessId where) {
        List<Collaborator> collaborators = repository.findAllByBusinessId(where.businessId());
        return success(collaborators);
    }

}
