package com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;

import java.util.Set;

public class ErrorOutputResponse<T> implements OutputResponse<T> {

    private Set<OutputError> errors;
    private String error;

    ErrorOutputResponse(Set<OutputError> errors) {
        this.errors = errors;
    }

    ErrorOutputResponse(String error) {
        this.error = error;
    }

    @Override
    public T getData() {
        return null;
    }

    @Override
    public Set<OutputError> getErrors() {
        return errors;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

}
