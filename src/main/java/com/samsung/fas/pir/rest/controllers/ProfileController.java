package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.rest.dto.profile.CProfileDTO;
import com.samsung.fas.pir.rest.dto.profile.RProfileDTO;
import com.samsung.fas.pir.rest.dto.profile.UProfileDTO;
import com.samsung.fas.pir.rest.services.ProfileService;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Api(name = "Profile Services", description = "Methods managing user profiles", group = "Profiles", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class ProfileController {
	private ProfileService pservice;

	@Autowired
	public ProfileController(ProfileService pservice) {
		this.pservice		= pservice;
	}

	// Get specific (GET)
	@ApiMethod(description="Get a sepcific profile")
	@ApiResponseObject(clazz = RProfileDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RProfileDTO> get(@ApiPathParam @PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(pservice.findOne(codedid));
	}

	// Get all (GET)
	@ApiMethod(description="Get all profiles saved in database")
	@ApiResponseObject(clazz = CProfileDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RProfileDTO>> getAll() {
		return ResponseEntity.ok(pservice.findAll());
	}

	// Get all (GET)
	@ApiMethod(description="Get all profiles saved in database (Pageable)")
	@ApiResponseObject(clazz = CProfileDTO.class)
	@RequestMapping(method=RequestMethod.GET, path = "/page")
	@ResponseBody
	public ResponseEntity<Page<RProfileDTO>> getAll(Pageable pageable) {
		return ResponseEntity.ok(pservice.findAll(pageable));
	}
	
	// Create (POST)
	@ApiMethod(description="Create a new profile")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> add(@ApiBodyObject @Valid @RequestBody CProfileDTO profile, @AuthenticationPrincipal Account principal) {
		return ResponseEntity.ok(pservice.save(profile, principal));
	}
	
	// Update (PUT)
	@ApiMethod(description="Update an existing profile")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @Valid @RequestBody UProfileDTO profile, @AuthenticationPrincipal Account principal) {
		return ResponseEntity.ok(pservice.update(profile, principal));
	}

	@ApiMethod(description="Search profiles using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<List<RProfileDTO>> search(@QuerydslPredicate(root = Profile.class) Predicate predicate) {
		return ResponseEntity.ok(pservice.findAll(predicate));
	}

	@ApiMethod(description="Search profiles using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<RProfileDTO>> search(@ApiPathParam @QuerydslPredicate(root = Profile.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(pservice.findAll(predicate, pageable));
	}
}