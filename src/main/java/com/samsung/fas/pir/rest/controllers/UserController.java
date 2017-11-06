package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.models.dto.user.CUserDTO;
import com.samsung.fas.pir.models.dto.user.RUserDTO;
import com.samsung.fas.pir.models.dto.user.UUserDTO;
import com.samsung.fas.pir.service.UsersService;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(name = "User Services", description = "Methods managing users", group = "Users", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/users")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class UserController {
	private						UsersService	uservice;

	@Autowired
	public UserController(UsersService uservice) {
		this.uservice		= uservice;
	}
	
	// Get all users (GET)
	@ApiMethod(description="Get all users saved in database")
	@ApiResponseObject(clazz = RUserDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RUserDTO>> getAll() {
		return ResponseEntity.ok(uservice.findAll());
	}
	
	// Get all users (GET)
	@ApiMethod(description="Get all users saved in database (pageable)")
	@ApiResponseObject(clazz = RUserDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="bypage")
	@ResponseBody
	public ResponseEntity<Page<RUserDTO>> getAll(Pageable pageable) {
		return ResponseEntity.ok(uservice.findAll(pageable));
	}
	
	// Get specific user (GET)
	@ApiMethod(description="Get specific user")
	@ApiResponseObject(clazz = RUserDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RUserDTO> get(@ApiPathParam @PathVariable("id") String codedid) {
		return ResponseEntity.ok(uservice.findByID(codedid));
	}
	
	// Create new user (POST)
	@ApiMethod(description="Create a new user (If user is PJUR, then send only OrgDTO, else if PFIS, send PersonDTO)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> add(@ApiBodyObject @Valid @RequestBody CUserDTO user) {
		uservice.save(user);
		return ResponseEntity.ok(null);
	}
	
	// Update user (PUT)
	@ApiMethod(description="Update an user (If user is PJUR, then send only OrgDTO, else if PFIS, send PersonDTO)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @Valid @RequestBody UUserDTO user) {
		uservice.update(user);
		return ResponseEntity.ok(null);
	}
}
