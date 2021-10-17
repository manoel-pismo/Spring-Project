package com.rachadel.config.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Manoel Rachadel Neto
 * @since  14 de out. de 2021
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidationErrorException(String message) {
		super(message);
	}
}
