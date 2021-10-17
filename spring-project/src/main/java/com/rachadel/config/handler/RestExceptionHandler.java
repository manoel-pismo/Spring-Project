package com.rachadel.config.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rachadel.config.error.ResponseError;
import com.rachadel.config.error.ResponseErrorDetail;
import com.rachadel.config.error.exception.ResourceNotFountException;
import com.rachadel.config.error.exception.ValidationErrorException;

/**
 * @author Manoel Rachadel Neto
 * @since 14 de out. de 2021
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFountException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFountException rnfException) {
		
		ResponseErrorDetail responseErrorDetail = ResponseErrorDetail.builder()
				.title("resource not found")
				.detail(rnfException.getMessage())
				.timestamp(new Date().getTime())
				.developerMessage(rnfException.getClass().getName())
				.build();

		ResponseError responseError = ResponseError.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.errors(Collections.singletonList(responseErrorDetail))
				.build();

		return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationErrorException.class)
	public ResponseEntity<?> handleValidationErrorException(ValidationErrorException veException) {
		
		ResponseErrorDetail responseErrorDetail = ResponseErrorDetail.builder()
				.title("a validation error occurred")
				.detail(veException.getMessage())
				.timestamp(new Date().getTime())
				.developerMessage(veException.getClass().getName())
				.build();

		ResponseError responseError = ResponseError.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.errors(Collections.singletonList(responseErrorDetail))
				.build();

		return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
	}	

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<ResponseErrorDetail> lstErrors = new ArrayList<>();
		Long now = new Date().getTime(); 
		
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			lstErrors.add(ResponseErrorDetail.builder()
					.title("validation error in field '" + fieldError.getField() + "'")
					.detail(fieldError.getDefaultMessage())
					.timestamp(now)
					.developerMessage(ex.getClass().getName())
					.build());
		}
		
		ResponseError responseError = ResponseError.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.errors(lstErrors).build();
		
		return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ResponseErrorDetail responseErrorDetail = ResponseErrorDetail.builder()
				.title("http message not readable")
				.detail(ex.getMessage())
				.timestamp(new Date().getTime())
				.developerMessage(ex.getClass().getName())
				.build();

		ResponseError responseError = ResponseError.builder()
				.status(status.value())
				.errors(Collections.singletonList(responseErrorDetail))
				.build();
		
		return new ResponseEntity<>(responseError, status);
	}
	@Override
	public ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ResponseErrorDetail responseErrorDetail = ResponseErrorDetail.builder()
				.title("internal exception")
				.detail(ex.getMessage())
				.timestamp(new Date().getTime())
				.developerMessage(ex.getClass().getName())
				.build();

		ResponseError responseError = ResponseError.builder()
				.status(status.value())
				.errors(Collections.singletonList(responseErrorDetail))
				.build();
		
		return new ResponseEntity<>(responseError, headers, status);
	}
}
