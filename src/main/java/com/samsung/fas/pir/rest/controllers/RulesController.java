package com.samsung.fas.pir.rest.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
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

import com.samsung.fas.pir.models.dto.RuleDTO;
import com.samsung.fas.pir.service.RuleService;

@Api(name = "Rules Services", description = "Methods managing user profiles rules (permissions)", group = "Profiles", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/rules")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class RulesController {
	@Autowired
	private		RuleService		rservice;
	
	// Get all (GET)
	@ApiMethod(description="Get all rules saved in database")
	@ApiResponseObject(clazz = RuleDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RuleDTO>> getAll() {
		return ResponseEntity.ok(rservice.findAll());
	}
	
	// Get specific (GET)
	@ApiMethod(description="Get rules by profile")
	@ApiResponseObject(clazz = RuleDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/by/profile/{id}")
	@ResponseBody
	public ResponseEntity<List<RuleDTO>> getByProfile(@ApiPathParam @PathVariable("id") UUID uuid) {
		return ResponseEntity.ok(rservice.findByProfileID(uuid));
	}
	
	// Get specific (GET)
	@ApiMethod(description="Get specific rule saved in database")
	@ApiResponseObject(clazz = RuleDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RuleDTO> get(@ApiPathParam @PathVariable("id") UUID uuid) {
		return ResponseEntity.ok(rservice.findOne(uuid));
	}
	
	// Create (POST)
	@ApiMethod(description="Save a rule in database (Permissions: Edit, View, Delete, Update) (Relative to: Web Application View Pages)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> add(@ApiBodyObject @Valid @RequestBody RuleDTO rule) {
		rservice.save(rule);
		return ResponseEntity.ok(null);
	}
	
	// Update (PUT)
	@ApiMethod(description="Update a specific rule (Permissions: Edit, View, Delete, Update) (Relative to: Web Application View Pages)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @Valid @RequestBody RuleDTO rule) {
		rservice.update(rule);
		return ResponseEntity.ok(null);
	}
	
	// Delete specific (DELETE)
	@ApiMethod(description="Delete a profile saved in database")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseBody
	public ResponseEntity<RuleDTO> delete(@ApiPathParam @PathVariable("id") UUID uuid) {
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
