package com.agendzy.api.core.gateway.common;

import com.agendzy.api.core.usecase.common.boundary.input.query.QueryInput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;

public interface FindOneGateway<T, W extends QueryInput> {

    OutputResponse<T> execute(W where);

}
