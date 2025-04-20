package com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public interface OutputResponse<T> {

    T getData();

    Set<OutputError> getErrors();

    String getError();

    boolean isError();

    boolean isSuccess();

    default Optional<T> getDataOptional() {
        return Optional.ofNullable(getData());
    }

    default <U> OutputResponse<U> map(Function<? super T, ? extends U> mapper) {
        if (isSuccess()) {
            return OutputResponseFactory.success(mapper.apply(getData()));
        }
        return OutputResponseFactory.error(getErrors());
    }

    default <U> OutputResponse<U> flatMap(Function<? super T, OutputResponse<U>> mapper) {
        if (isSuccess()) {
            return mapper.apply(getData());
        }
        return OutputResponseFactory.error(getErrors());
    }

    default T orElse(T other) {
        return isSuccess() ? getData() : other;
    }

    default T orElseGet(Supplier<? extends T> supplier) {
        return isSuccess() ? getData() : supplier.get();
    }

    default OutputResponse<T> orElseThrow(Function<OutputError, ? extends RuntimeException> exceptionFunction) {
        if (isError()) {
            var firstError = getErrors().stream().findFirst()
                    .orElseThrow(() -> new IllegalStateException("No errors found in an error response."));
            throw exceptionFunction.apply(firstError);
        }
        return this;
    }

}