package com.agendzy.api.core.gateway.common;

import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;

import java.util.List;

public interface FindAllGateway<T> {

    OutputResponse<List<T>> execute();

}
