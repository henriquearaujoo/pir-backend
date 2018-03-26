package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.exception.RESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
	private		static 			Logger 			Log			= LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
		return ResponseEntity.badRequest().body(e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleException(ConstraintViolationException e) {
		return ResponseEntity.badRequest().body(e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
	}

	@ExceptionHandler(RESTException.class)
	public ResponseEntity<?> handleException(RESTException e) {
		Log.error(e.toString());
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(RequestRejectedException.class)
	public ResponseEntity<?> handleException(RequestRejectedException e) {
		Log.error(e.toString());
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException e) {
		Log.error(e.getMessage());
		return ResponseEntity.badRequest().body("method." + e.getMethod().toLowerCase() + ".not.supported");
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleException(AuthenticationException e) {
		Log.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
		Log.error(e.getRootCause().getMessage());
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getRootCause() instanceof RESTException? e.getRootCause().getMessage() : null);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleException(RuntimeException e) {
		Log.error(e.getMessage());
		e.printStackTrace();
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleException(DataIntegrityViolationException e) {
		if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
			return new ResponseEntity<>(((org.hibernate.exception.ConstraintViolationException) e.getCause()).getConstraintName() + ".found", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>("database.violation", HttpStatus.BAD_REQUEST);
	}
}
