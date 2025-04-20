package com.agendzy.api.dataprovider.repository.common.query;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@RequiredArgsConstructor
public class FindOneUserById implements FindOneGateway<User, WhereId> {

    private final UserRepository repository;

    @Override
    public OutputResponse<User> execute(WhereId where) {
        return repository.findById(where.id())
                .map(OutputResponseFactory::success)
                .orElseGet(() -> error(entityNotFound("User not found with ID: " + where.id())));
    }

}
