package com.agendzy.api.dataprovider.repository.common;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class SaveUser implements SaveGateway<User> {

    private final UserRepository repository;

    @Override
    public OutputResponse<User> execute(User user) {
        return success(repository.save(user));
    }

}
