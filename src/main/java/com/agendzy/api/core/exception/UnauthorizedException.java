package com.agendzy.api.core.exception;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(OutputError error) {
        super(error.getDetail());
    }

}
