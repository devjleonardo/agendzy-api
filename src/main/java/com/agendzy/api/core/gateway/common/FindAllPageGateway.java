package com.agendzy.api.core.gateway.common;

import com.agendzy.api.core.usecase.common.boundary.input.query.PageRequestInput;
import com.agendzy.api.core.usecase.common.boundary.input.query.QueryInput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.query.PageResultOutput;

public interface FindAllPageGateway<T, Q extends QueryInput> {

    OutputResponse<PageResultOutput<T>> execute(Q queryInput, PageRequestInput pageableInput);

}
