package com.agendzy.api.core.usecase.common.boundary.output;

import com.agendzy.api.core.usecase.common.boundary.output.data.DomainError;

public interface OutputError {

    String getType();
    String getTitle();
    String getDetail();

    static OutputError entityNotFound(DomainError domainError) {
        return new DefaultOutputError(OutputErrorType.ENTITY_NOT_FOUND, "Resource Not Found",
                domainError.getDetail());
    }

    static OutputError entityNotFound(String detail) {
        return new DefaultOutputError(OutputErrorType.ENTITY_NOT_FOUND, "Resource Not Found", detail);
    }

    static OutputError businessRuleViolation(String detail) {
        return new DefaultOutputError(OutputErrorType.BUSINESS_RULE_VIOLATION, "Operation Not Allowed", detail);
    }

    static OutputError unauthorized(String detail) {
        return new DefaultOutputError(OutputErrorType.UNAUTHORIZED, "Unauthorized Access", detail);
    }

    static OutputError unauthorized(DomainError domainError) {
        return new DefaultOutputError(OutputErrorType.UNAUTHORIZED, "Unauthorized Access",
                domainError.getDetail());

    }

}
