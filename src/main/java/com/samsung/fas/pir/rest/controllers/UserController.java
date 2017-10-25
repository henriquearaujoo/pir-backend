package com.samsung.fas.pir.rest.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsung.fas.pir.dto.UserDTO;
import com.samsung.fas.pir.service.UsersService;

@Controller
@RequestMapping("/users")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class UserController {
	@Autowired
	private		UsersService	uservice;
	
	// Get all users (GET)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<UserDTO>> getAll() {
		return ResponseEntity.ok(uservice.findAll());
	}
	
	// Get specific user (GET)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<UserDTO> get(@PathVariable("id") UUID uuid) {
		return ResponseEntity.ok(uservice.findByID(uuid));
	}
	
	// Create new user (POST)
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> add(@Valid @RequestBody UserDTO user) {
		uservice.save(user);
		return ResponseEntity.ok(null);
	}
	
	// Update user (PUT)
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@Valid @RequestBody UserDTO user) {
		uservice.update(user);
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
