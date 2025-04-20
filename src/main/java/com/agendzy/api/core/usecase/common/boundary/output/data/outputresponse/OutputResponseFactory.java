package com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;

import java.util.Set;
import java.util.function.Supplier;

public class OutputResponseFactory {

    private OutputResponseFactory() {}

    public static <T> OutputResponse<T> success(T value) {
        return new SuccessOutputResponse<>(value);
    }

    public static OutputResponse<Void> success() {
        return new SuccessOutputResponse<>(null);
    }

    public static <T> OutputResponse<T> error(OutputError... error) {
        return new ErrorOutputResponse<>(Set.of(error));
    }

    public static <T> OutputResponse<T> error(Set<OutputError> errors) {
        return new ErrorOutputResponse<>(errors);
    }

    public static <T> OutputResponse<T> error(String error) {
        return new ErrorOutputResponse<>(error);
    }

    public static <T> OutputResponse<T> error(Supplier<RuntimeException> exceptionSupplier) {
        throw exceptionSupplier.get();
    }

    public static <T> OutputResponse<T> errorBusinessRule(String message) {
        return error(OutputError.businessRuleViolation(message));
    }

}
