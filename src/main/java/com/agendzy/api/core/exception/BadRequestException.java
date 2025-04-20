package com.agendzy.api.core.exception;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;
import lombok.Getter;

import java.util.Set;

@Getter
public class BadRequestException extends RuntimeException {

	public BadRequestException(String errorMessage) {
		super(errorMessage);
	}

	public BadRequestException(OutputError error) {
		super(error.getDetail());
	}

	public BadRequestException(Set<OutputError> errors) {
		super(errors.stream()
				.findFirst()
				.map(OutputError::getDetail)
				.orElse(""));
	}

}
