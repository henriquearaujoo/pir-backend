package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.rest.dto.rule.RRuleDTO;
import com.samsung.fas.pir.rest.dto.rule.URuleDTO;
import com.samsung.fas.pir.rest.services.RuleService;
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

@Api(name = "Rules Services", description = "Methods managing user profiles rules (permissions)", group = "Profiles", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/rest/rules")
@Produces(MediaType.APPLICATION_JSON)
public class RulesController {
	private		RuleService		rservice;

	@Autowired
	public RulesController(RuleService rservice) {
		this.rservice		= rservice;
	}

	@ApiMethod(description="Get all rules saved in database")
	@ApiResponseObject(clazz = RRuleDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RRuleDTO>> getAll() {
		return ResponseEntity.ok(rservice.findAll());
	}

	@ApiMethod(description="Get all rules saved in database (pageable)")
	@ApiResponseObject(clazz = RRuleDTO.class)
	@RequestMapping(method=RequestMethod.GET, path = "/page")
	@ResponseBody
	public ResponseEntity<Page<RRuleDTO>> getAll(Pageable pageable) {
		return ResponseEntity.ok(rservice.findAll(pageable));
	}

	@ApiMethod(description="Get specific rule saved in database")
	@ApiResponseObject(clazz = RRuleDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RRuleDTO> get(@ApiPathParam @PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(rservice.findOne(codedid));
	}

	@ApiMethod(description="Update a specific rule (Permissions: Edit, View, Delete, Update) (Relative to: Web Application View Pages)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @Valid @RequestBody URuleDTO rule, @AuthenticationPrincipal Account principal) {
		return ResponseEntity.ok(rservice.update(rule, principal));
	}

	@ApiMethod(description="Delete a profile saved in database")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseBody
	public ResponseEntity<?> delete(@ApiPathParam @PathVariable("id") UUID codedid) {
		rservice.delete(codedid);
		return ResponseEntity.ok(null);
	}

	@ApiMethod(description="Search rules using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<List<RRuleDTO>> search(@QuerydslPredicate(root = Rule.class) Predicate predicate) {
		return ResponseEntity.ok(rservice.findAll(predicate));
	}

	@ApiMethod(description="Search rules using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<RRuleDTO>> search(@ApiPathParam @QuerydslPredicate(root = Rule.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(rservice.findAll(predicate, pageable));
	}
}
