package com.agendzy.api.entrypoint.http.exception.message;

import lombok.Getter;

@Getter
public enum HttpErrorMessage {

    SYSTEM_ERROR("System Error"),
    INTERNAL_SERVER_ERROR("An unexpected error occurred. Please try again, or contact support if the issue persists."),
    INVALID_FIELDS("Invalid Fields"),
    INVALID_DATA("Invalid Data"),
    DETAIL_UNAVAILABLE("Detail Not Available"),
    INVALID_FIELD_FORMAT("Invalid value '%s' for the field '%s'. Expected type: %s."),
    BAD_REQUEST("Invalid Request"),
    PROPERTY_NOT_FOUND("The property '%s' does not exist. Please review your request and try again."),
    MALFORMED_REQUEST_BODY("The request body is malformed or contains invalid data."),
    INVALID_URL_PARAMETER_VALUE("The parameter '%s' in the URL has an invalid value '%s'. Expected type: %s."),
    INVALID_URL_PARAMETER_TYPE("Invalid Parameter Type in URL"),
    ENDPOINT_NOT_FOUND("The endpoint '%s' does not exist."),
    ENDPOINT_NOT_FOUND_TITLE("Endpoint Not Found"),
    SEARCH_FAILURE("Search Error"),
    OPERATION_NOT_ALLOWED("Operation Not Allowed"),
    METHOD_NOT_SUPPORTED("Operation Not Supported"),
    UNSUPPORTED_HTTP_METHOD("The HTTP method '%s' is not supported for this endpoint."),
    RESOURCE_NOT_FOUND_GENERIC("Resource Not Found"),
    UNAUTHORIZED_ACCESS("Unauthorized Access");

    private final String message;

    HttpErrorMessage(String message) {
        this.message = message;
    }

}
