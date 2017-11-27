package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@CrossOrigin
public class BaseController {
	private		static 			Logger 			Log			= LoggerFactory.getLogger(UserController.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
		List<FieldError> fieldErrors 	= e.getBindingResult().getFieldErrors();
		Set<String> errors				= new HashSet<>();
		fieldErrors.forEach((error) -> errors.add(error.getDefaultMessage()));
		Log.error(errors.toString());
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(RESTRuntimeException.class)
	public ResponseEntity<?> handleException(RESTRuntimeException e) {
		Log.error(e.toString());
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
		Log.error(e.getRootCause().getMessage());
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getRootCause() instanceof RESTRuntimeException? e.getRootCause().getMessage() : null);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleException(RuntimeException e) {
		Log.error(e.getMessage());
		e.printStackTrace();
		return ResponseEntity.badRequest().build();
	}
}
