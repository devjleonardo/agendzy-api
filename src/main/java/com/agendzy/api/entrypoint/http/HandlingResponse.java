package com.agendzy.api.entrypoint.http;

import com.agendzy.api.core.exception.BusinessRuleException;
import com.agendzy.api.core.exception.EntityNotFoundException;
import com.agendzy.api.core.exception.UnauthorizedException;
import com.agendzy.api.core.usecase.common.boundary.output.OutputError;
import com.agendzy.api.core.usecase.common.boundary.output.OutputErrorType;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public abstract class HandlingResponse {

    private HandlingResponse() {
    }

    public static <T> ResponseEntity<Object> execute(OutputResponse<T> response) {
        process(response);
        return ResponseEntity.ok(response.getData());
    }

    public static <T, R> ResponseEntity<Object> execute(OutputResponse<T> response, Function<T, R> transformer) {
        process(response);
        R transformedData = transformer.apply(response.getData());
        return ResponseEntity.ok(transformedData);
    }

    private static <T> void process(OutputResponse<T> response) {
        if (response.isError()) {
            if (response.getError() != null && !response.getError().isBlank()) {

            } else {
                handleErrors(response.getErrors());
            }
        }

        if (Objects.isNull(response.getData())) {
            ResponseEntity.noContent().build();
        }
    }

    private static void handleErrors(Set<OutputError> errors) {
        errors.forEach(error -> {
            OutputErrorType errorType = OutputErrorType.valueOf(error.getType());
            switch (errorType) {
                case ENTITY_NOT_FOUND -> throw new EntityNotFoundException(error);
                case BUSINESS_RULE_VIOLATION -> throw new BusinessRuleException(error);
                case UNAUTHORIZED -> throw new UnauthorizedException(error);
                default -> throw new RuntimeException(error.getDetail());
            }
        });
    }

}
