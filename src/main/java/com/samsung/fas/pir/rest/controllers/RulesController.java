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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsung.fas.pir.dto.RuleDTO;
import com.samsung.fas.pir.service.RuleService;

@Controller
@RequestMapping("/rules")
@Produces(MediaType.APPLICATION_JSON)
public class RulesController {
	@Autowired
	private		RuleService		rservice;
	
	// Get all (GET)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RuleDTO>> getAll() {
		return ResponseEntity.ok(rservice.findAll());
	}
	
	// Get specific (GET)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RuleDTO> get(@PathVariable("id") UUID uuid) {
		return ResponseEntity.ok(rservice.findOne(uuid));
	}
	
	// Create (POST)
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> add(@Valid @RequestBody RuleDTO rule) {
		rservice.save(rule);
		return ResponseEntity.ok(null);
	}
	
	// Update (PUT)
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@Valid @RequestBody RuleDTO rule) {
		rservice.update(rule);
		return ResponseEntity.ok(null);
	}
	
	// Get specific (GET)
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseBody
	public ResponseEntity<RuleDTO> delete(@PathVariable("id") UUID uuid) {
		rservice.delete(uuid);
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
