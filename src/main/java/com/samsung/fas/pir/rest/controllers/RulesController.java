package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.models.dto.rule.CRuleDTO;
import com.samsung.fas.pir.models.dto.rule.RRuleDTO;
import com.samsung.fas.pir.models.dto.rule.URuleDTO;
import com.samsung.fas.pir.service.RuleService;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(name = "Rules Services", description = "Methods managing user profiles rules (permissions)", group = "Profiles", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/rules")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class RulesController {
	private		RuleService		rservice;

	@Autowired
	public RulesController(RuleService rservice) {
		this.rservice		= rservice;
	}
	
	// Get all (GET)
	@ApiMethod(description="Get all rules saved in database")
	@ApiResponseObject(clazz = RRuleDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RRuleDTO>> getAll() {
		return ResponseEntity.ok(rservice.findAll());
	}
	
	// Get specific (GET)
	@ApiMethod(description="Get rules by profile")
	@ApiResponseObject(clazz = RRuleDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/by/profile/{id}")
	@ResponseBody
	public ResponseEntity<List<RRuleDTO>> getByProfile(@ApiPathParam @PathVariable("id") String codedid) {
		return ResponseEntity.ok(rservice.findByProfileID(codedid));
	}
	
	// Get specific (GET)
	@ApiMethod(description="Get specific rule saved in database")
	@ApiResponseObject(clazz = RRuleDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RRuleDTO> get(@ApiPathParam @PathVariable("id") String codedid) {
		return ResponseEntity.ok(rservice.findOne(codedid));
	}
	
	// Create (POST)
	@ApiMethod(description="Save a rule in database (Permissions: Edit, View, Delete, Update) (Relative to: Web Application View Pages)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> add(@ApiBodyObject @Valid @RequestBody CRuleDTO rule) {
		rservice.save(rule);
		return ResponseEntity.ok(null);
	}
	
	// Update (PUT)
	@ApiMethod(description="Update a specific rule (Permissions: Edit, View, Delete, Update) (Relative to: Web Application View Pages)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @Valid @RequestBody URuleDTO rule) {
		rservice.update(rule);
		return ResponseEntity.ok(null);
	}
	
	// Delete specific (DELETE)
	@ApiMethod(description="Delete a profile saved in database")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseBody
	public ResponseEntity<?> delete(@ApiPathParam @PathVariable("id") String codedid) {
		rservice.delete(codedid);
		return ResponseEntity.ok(null);
	}
}
