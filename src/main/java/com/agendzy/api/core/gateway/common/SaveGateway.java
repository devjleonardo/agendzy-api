package com.agendzy.api.core.gateway.common;

import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;

public interface SaveGateway<T> {

    OutputResponse<T> execute(T value);

}
