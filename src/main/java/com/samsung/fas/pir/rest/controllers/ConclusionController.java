package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.rest.dto.conclusion.CConclusionDTO;
import com.samsung.fas.pir.rest.dto.conclusion.RConclusionDTO;
import com.samsung.fas.pir.rest.dto.conclusion.UConclusionDTO;
import com.samsung.fas.pir.rest.services.ConclusionService;
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

@Api(name = "Conclusion Service", description = "Methods for managing chapter's conclusion", group = "Chapters", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/chapters/conclusion")
@Produces(MediaType.APPLICATION_JSON)
public class ConclusionController {
	private ConclusionService service;

	@Autowired
	public ConclusionController(ConclusionService service) {
		this.service = service;
	}

	@ApiMethod(description="Get all chapters' greetings from database")
	@ApiResponseObject(clazz = RConclusionDTO.class)
	@RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RConclusionDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@ApiMethod(description="Get all chapters' greetings from database (pageable)")
	@ApiResponseObject(clazz = RConclusionDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/page")
	@ResponseBody
	public ResponseEntity<Page<RConclusionDTO>> getAll(@ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@ApiMethod(description="Get specific chapter's greeting")
	@ApiResponseObject(clazz = RConclusionDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RConclusionDTO> get(@ApiPathParam @PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(service.findOne(codedid));
	}

	@ApiMethod(description="Search chapters' greetings using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<List<RConclusionDTO>> search(@QuerydslPredicate(root = Conclusion.class) Predicate predicate) {
		return ResponseEntity.ok(service.findAll(predicate));
	}

	@ApiMethod(description="Search chapters' greetings using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<RConclusionDTO>> search(@ApiPathParam @QuerydslPredicate(root = Conclusion.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(predicate, pageable));
	}

	@ApiMethod(description="Create a new greeting")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@ApiBodyObject @RequestBody @Valid CConclusionDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@ApiMethod(description="Update a greeting")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UConclusionDTO dto) {
		service.update(dto);
		return ResponseEntity.ok(service.update(dto));
	}
}
