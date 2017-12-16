package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.dto.intervention.CInterventionDTO;
import com.samsung.fas.pir.models.dto.intervention.RInterventionDTO;
import com.samsung.fas.pir.models.dto.intervention.UInterventionDTO;
import com.samsung.fas.pir.models.entity.Intervention;
import com.samsung.fas.pir.services.InterventionService;
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

@Api(name = "Intervention Services", description = "Methods for managing chapters' intervention", group = "Chapters", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/chapters/intervention")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class InterventionController {
	private InterventionService service;

	@Autowired
	public InterventionController(InterventionService service) {
		this.service = service;
	}

	@ApiMethod(description = "Get all chapters' greetings from database")
	@ApiResponseObject(clazz = RInterventionDTO.class)
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RInterventionDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@ApiMethod(description = "Get all chapters' greetings from database (pageable)")
	@ApiResponseObject(clazz = RInterventionDTO.class)
	@RequestMapping(method = RequestMethod.GET, path = "/page")
	@ResponseBody
	public ResponseEntity<Page<RInterventionDTO>> getAll(@ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@ApiMethod(description = "Get specific chapter's greeting")
	@ApiResponseObject(clazz = RInterventionDTO.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ResponseEntity<RInterventionDTO> get(@ApiPathParam @PathVariable("id") String codedid) {
		return ResponseEntity.ok(service.findOne(codedid));
	}

	@ApiMethod(description = "Search chapters' greetings using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method = RequestMethod.GET, value = "/search")
	@ResponseBody
	public ResponseEntity<List<RInterventionDTO>> search(@QuerydslPredicate(root = Intervention.class) Predicate predicate) {
		return ResponseEntity.ok(service.findAll(predicate));
	}

	@ApiMethod(description = "Search chapters' greetings using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method = RequestMethod.GET, value = "/search/page")
	@ResponseBody
	public ResponseEntity<Page<RInterventionDTO>> search(@ApiPathParam @QuerydslPredicate(root = Intervention.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(predicate, pageable));
	}

	@ApiMethod(description = "Create a new greeting")
	@ApiResponseObject
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@ApiBodyObject @RequestBody @Valid CInterventionDTO dto) {
		service.save(dto);
		return ResponseEntity.ok(null);
	}

	@ApiMethod(description = "Update a greeting")
	@ApiResponseObject
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UInterventionDTO dto) {
		service.update(dto);
		return ResponseEntity.ok(null);
	}
}