package com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;

import java.util.Set;

public class SuccessOutputResponse<T> implements OutputResponse<T> {

    private final T data;

    SuccessOutputResponse(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Set<OutputError> getErrors() {
        return null;
    }

    @Override
    public String getError() {
        return "";
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

}
