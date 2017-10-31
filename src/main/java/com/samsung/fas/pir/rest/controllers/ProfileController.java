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

import com.samsung.fas.pir.models.dto.PageDTO;
import com.samsung.fas.pir.models.dto.ProfileDTO;
import com.samsung.fas.pir.models.dto.UserDTO;
import com.samsung.fas.pir.service.ProfileService;

@Api(name = "Profile Services", description = "Methods managing user profiles", group = "Profiles", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/profiles")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class ProfileController {
	@Autowired
	private ProfileService pservice;

	// Get all (GET)
	@ApiMethod(description="Get all profiles saved in database")
	@ApiResponseObject(clazz = ProfileDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ProfileDTO>> getAll() {
		return ResponseEntity.ok(pservice.findAll());
	}
	
	// Get all actives (GET)
	@ApiMethod(description="Get all active profiles")
	@ApiResponseObject(clazz = ProfileDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="active")
	@ResponseBody
	public ResponseEntity<List<ProfileDTO>> getAllActive() {
		return ResponseEntity.ok(pservice.findAllActive());
	}
	
	// Get specific (GET)
	@ApiMethod(description="Get a sepcific profile")
	@ApiResponseObject(clazz = ProfileDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<ProfileDTO> get(@ApiPathParam @PathVariable("id") UUID uuid) {
		return ResponseEntity.ok(pservice.findOne(uuid));
	}
	
	// Get users from specified profile (GET)
	@ApiMethod(description="Get users from a specific profile")
	@ApiResponseObject(clazz = UserDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}/users")
	@ResponseBody
	public ResponseEntity<List<UserDTO>> getUsers(@ApiPathParam @PathVariable("id") UUID uuid) {
		return ResponseEntity.ok(pservice.findUsersByProfileID(uuid));
	}
	
	// Get pages from specified profile (GET)
	@ApiMethod(description="Get pages from a specific profile")
	@ApiResponseObject(clazz = PageDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}/pages")
	@ResponseBody
	public ResponseEntity<List<PageDTO>> getPages(@ApiPathParam @PathVariable("id") UUID uuid) {
		return ResponseEntity.ok(pservice.findPagesByProfileID(uuid));
	}
	
	// Create (POST)
	@ApiMethod(description="Create a new profile")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> add(@ApiBodyObject @Valid @RequestBody ProfileDTO profile) {
		pservice.save(profile);
		return ResponseEntity.ok(null);
	}
	
	// Update (PUT)
	@ApiMethod(description="Update an existing profile")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @Valid @RequestBody ProfileDTO profile) {
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