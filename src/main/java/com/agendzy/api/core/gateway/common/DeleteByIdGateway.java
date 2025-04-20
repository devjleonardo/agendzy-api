package com.agendzy.api.core.gateway.common;

import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;

import java.util.Optional;

public interface DeleteByIdGateway<T> {

    OutputResponse<Optional<T>> execute(String id);


}
