package com.agendzy.api.entrypoint.http.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class HttpErrorResponse {

    private Integer status;
    private String errorCode;
    private String title;
    private String detail;
    private List<FieldError> errors;

    @Builder
    @Getter
    public static class FieldError {

        private String field;
        private String message;

    }

}
