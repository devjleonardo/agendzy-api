package com.agendzy.api.core.gateway.common;

import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;

public interface DeleteGateway<T> {

    OutputResponse<Void> execute(T t);

}
