package com.agendzy.api.core.exception;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;
import lombok.Getter;

import java.util.Set;

@Getter
public class BusinessRuleException extends RuntimeException {

	public BusinessRuleException(OutputError error) {
		super(error.getDetail());
	}

	public BusinessRuleException(Set<OutputError> errors) {
		super(errors.stream()
				.findFirst()
				.map(OutputError::getDetail)
				.orElse(""));
	}

}
