package com.agendzy.api.dataprovider.repository.common.query;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WherePhoneNumber;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.common.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@AllArgsConstructor
public class FindOneUserByPhoneNumber implements FindOneGateway<User, WherePhoneNumber> {

    private final UserRepository repository;

    public OutputResponse<User> execute(WherePhoneNumber where) {
        return repository
                .findFirstByPhoneNumber(where.phoneNumber())
                .map(OutputResponseFactory::success)
                .orElseGet(() -> error(entityNotFound("User not found with phone number: " + where.phoneNumber())));
    }

}
