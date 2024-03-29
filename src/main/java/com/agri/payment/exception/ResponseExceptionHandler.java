package com.agri.payment.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	// private final MessageSource messageSource;

	public ResponseExceptionHandler(MessageSource messageSource) {
		// this.messageSource = messageSource;
	}

	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// Get all errors
		List<ApiSubError> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(x -> new ApiValidationError(x.getObjectName(), x.getField(), x.getRejectedValue(),
						x.getDefaultMessage()))
				.collect(Collectors.toList());
		ApiError apiError = new ApiError(status, "Invalid Input", ex);
		apiError.setSubErrors(errors);
		return buildResponseEntity(apiError);
	}

	// other exception handlers below
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleBadRequestException(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
		log.error("Exception caught in advice " + ex.getMessage(), ex);
		return buildResponseEntity(apiError);
	}
}
