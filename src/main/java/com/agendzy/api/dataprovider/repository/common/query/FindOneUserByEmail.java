package com.agendzy.api.dataprovider.repository.common.query;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.common.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@AllArgsConstructor
public class FindOneUserByEmail implements FindOneGateway<User, WhereEmail> {

    private final UserRepository repository;

    public OutputResponse<User> execute(WhereEmail where) {
        return repository
                .findFirstByEmail(where.email())
                .map(OutputResponseFactory::success)
                .orElseGet(() -> error(entityNotFound("User not found with email: " + where.email())));
    }

}
