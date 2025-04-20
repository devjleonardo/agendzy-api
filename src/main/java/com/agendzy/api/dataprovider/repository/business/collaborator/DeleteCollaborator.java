package com.agendzy.api.dataprovider.repository.business.collaborator;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.common.DeleteGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class DeleteCollaborator implements DeleteGateway<Collaborator> {

    private final CollaboratorRepository repository;

    @Override
    public OutputResponse<Void> execute(Collaborator collaborator) {
        repository.delete(collaborator);
        return success();
    }

}
