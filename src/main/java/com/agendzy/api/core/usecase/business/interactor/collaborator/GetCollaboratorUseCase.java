package com.agendzy.api.core.usecase.business.interactor.collaborator;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.business.boundary.output.collaborator.GetOneCollaboratorOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class GetCollaboratorUseCase {

    private final FindOneGateway<Collaborator, WhereBusinessIdAndEntityId> findCollaboratorByBusinessAndId;

    public OutputResponse<GetOneCollaboratorOutput> execute(String businessId, String collaboratorId) {
        checkAccessToBusiness(businessId);

        var collaboratorResponse = findCollaboratorByBusinessAndId.execute(
                new WhereBusinessIdAndEntityId(businessId, collaboratorId)
        );

        if (collaboratorResponse.isError()) {
            return error(collaboratorResponse.getErrors());
        }

        return success(GetOneCollaboratorOutput.of(collaboratorResponse.getData()));
    }

}
