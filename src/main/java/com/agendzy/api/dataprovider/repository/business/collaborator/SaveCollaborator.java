package com.agendzy.api.dataprovider.repository.business.collaborator;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class SaveCollaborator implements SaveGateway<Collaborator> {

    private final CollaboratorRepository repository;

    @Override
    public OutputResponse<Collaborator> execute(Collaborator collaborator) {
        return success(repository.save(collaborator));
    }

}
