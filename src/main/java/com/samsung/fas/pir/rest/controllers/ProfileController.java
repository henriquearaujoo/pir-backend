package com.samsung.fas.pir.rest.controllers;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsung.fas.pir.dto.ProfileDTO;
import com.samsung.fas.pir.dto.UserDTO;
import com.samsung.fas.pir.service.ProfileService;

@Controller
@RequestMapping("/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class ProfileController {
	@Autowired
	private ProfileService pservice;

	// Get all users (GET)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ProfileDTO>> getAllUsers() {
		return ResponseEntity.ok(pservice.findAll());
	}
	
	// Create new user (POST)
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addUser(@Valid @RequestBody ProfileDTO profile) {
		pservice.save(profile);
		return ResponseEntity.ok(null);
	}
	
	// Update user (PUT)
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateUser(@Valid @RequestBody ProfileDTO profile) {
		pservice.update(profile);
		return ResponseEntity.ok(null);
	}
	
	/***********************************************************************************/
	// Exceptions
	/***********************************************************************************/
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
	    List<FieldError> 	fieldErrors 	= e.getBindingResult().getFieldErrors();
	    Set<String>			errors			= new HashSet<>();
	    fieldErrors.forEach((error) -> errors.add(error.getDefaultMessage()));
	    return ResponseEntity.badRequest().body(errors);
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
		return ResponseEntity.badRequest().body(e.getRootCause().getMessage());
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(RuntimeException e) {
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getMessage());
    }
}