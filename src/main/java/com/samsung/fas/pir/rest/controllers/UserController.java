package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.rest.dto.user.*;
import com.samsung.fas.pir.rest.services.UsersService;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Api(name = "User Services", description = "Methods managing users", group = "Users", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/rest/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
	private		UsersService	uservice;

	@Autowired
	public UserController(UsersService uservice) {
		this.uservice		= uservice;
	}

	@ApiMethod(description="Get all users saved in database")
	@ApiResponseObject(clazz = RUserDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RUserDTO>> getAll() {
		return ResponseEntity.ok(uservice.findAll());
	}

	@ApiMethod(description="Get all users saved in database (pageable)")
	@ApiResponseObject(clazz = RUserDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/page")
	@ResponseBody
	public ResponseEntity<Page<RUserDTO>> getAll(@ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(uservice.findAll(pageable));
	}

	@ApiMethod(description="Get specific user")
	@ApiResponseObject(clazz = RUserDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RUserDTO> get(@ApiPathParam @PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(uservice.findOne(codedid));
	}

	@ApiMethod(description="Create a new user (PJUR)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST, path = "/entity")
	@ResponseBody
	public ResponseEntity<?> add(@ApiBodyObject @RequestBody @Valid CPJurDTO user) {
		return ResponseEntity.ok(uservice.save(user));
	}
	@ApiMethod(description="Create a new user (PFIS)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST, path = "/person")
	@ResponseBody
	public ResponseEntity<?> add(@ApiBodyObject @RequestBody @Valid CPFisDTO user) {
		return ResponseEntity.ok(uservice.save(user));
	}

	@ApiMethod(description="Update an user (PFIS)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT, path = "/person")
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UPFisDTO user) {
		return ResponseEntity.ok(uservice.update(user));
	}

	@ApiMethod(description="Update an user (PJUR)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT, path = "/entity")
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UPJurDTO user) {
		return ResponseEntity.ok(uservice.update(user));
	}

	@ApiMethod(description="Search users using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<List<RUserDTO>> search(@QuerydslPredicate(root = User.class) Predicate predicate) {
		return ResponseEntity.ok(uservice.findAll(predicate));
	}

	@ApiMethod(description="Search users using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<RUserDTO>> search(@ApiPathParam @QuerydslPredicate(root = User.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(uservice.findAll(predicate, pageable));
	}
}
