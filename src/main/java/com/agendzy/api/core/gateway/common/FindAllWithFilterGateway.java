package com.agendzy.api.core.gateway.common;

import com.agendzy.api.core.usecase.common.boundary.input.query.QueryInput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;

import java.util.List;

public interface FindAllWithFilterGateway <T, Q extends QueryInput> {

    OutputResponse<List<T>> execute(Q queryInput);

}
