package com.agendzy.api.core.exception;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(OutputError error) {
		super(error.getDetail());
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

}
