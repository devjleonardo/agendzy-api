package com.agendzy.api.dataprovider.repository.business.query;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.business.BusinessRepository;
import com.agendzy.api.dataprovider.repository.common.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@AllArgsConstructor
public class FindOneBusinessByOwnerEmail implements FindOneGateway<Business, WhereEmail> {

    private final BusinessRepository repository;

    public OutputResponse<Business> execute(WhereEmail where) {
        return repository
                .findByOwnerUserEmail(where.email())
                .map(OutputResponseFactory::success)
                .orElseGet(() -> error(entityNotFound("Business not found with owner email: " + where.email())));
    }

}
