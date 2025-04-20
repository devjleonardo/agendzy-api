package com.agendzy.api.core.usecase.business.interactor.collaborator;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessId;
import com.agendzy.api.core.usecase.business.boundary.output.collaborator.CollaboratorOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class ListCollaboratorsUseCase {

    private final FindAllWithFilterGateway<Collaborator, WhereBusinessId> findAllCollaboratorsByBusinessId;

    public OutputResponse<List<CollaboratorOutput>> execute(String businessId) {
        checkAccessToBusiness(businessId);

        var servicesResponse = findAllCollaboratorsByBusinessId.execute(new WhereBusinessId(businessId));

        if (servicesResponse.isError()) {
            return error(servicesResponse.getErrors());
        }

        List<CollaboratorOutput> outputs = servicesResponse.getData().stream()
                .map(CollaboratorOutput::of)
                .toList();

        return success(outputs);
    }

}
