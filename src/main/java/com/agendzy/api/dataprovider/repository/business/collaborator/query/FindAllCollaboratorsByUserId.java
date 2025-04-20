package com.agendzy.api.dataprovider.repository.business.collaborator.query;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereUserId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.dataprovider.repository.business.collaborator.CollaboratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class FindAllCollaboratorsByUserId implements FindAllWithFilterGateway<Collaborator, WhereUserId> {

    private final CollaboratorRepository repository;

    @Override
    public OutputResponse<List<Collaborator>> execute(WhereUserId where) {
        List<Collaborator> collaborators = repository.findCollaboratorsByUserId(where.userId());
        return success(collaborators);
    }

}
