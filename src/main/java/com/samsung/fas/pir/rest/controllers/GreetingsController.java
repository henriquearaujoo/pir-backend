package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.dto.greetings.CGreetingsDTO;
import com.samsung.fas.pir.models.dto.greetings.RGreetingsDTO;
import com.samsung.fas.pir.models.dto.greetings.UGreetingsDTO;
import com.samsung.fas.pir.models.entity.Greetings;
import com.samsung.fas.pir.models.services.GreetingsService;
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

@Api(name = "Greetings Services", description = "Methods for managing chapters' greetings", group = "Chapters", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/chapters/greetings/")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, allowCredentials = "true")
public class GreetingsController {
	private GreetingsService service;

	@Autowired
	public GreetingsController(GreetingsService service) {
		this.service = service;
	}

	@ApiMethod(description="Get all chapters' greetings from database")
	@ApiResponseObject(clazz = RGreetingsDTO.class)
	@RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RGreetingsDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@ApiMethod(description="Get all chapters' greetings from database (pageable)")
	@ApiResponseObject(clazz = RGreetingsDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="page/")
	@ResponseBody
	public ResponseEntity<Page<RGreetingsDTO>> getAll(@ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@ApiMethod(description="Get specific chapter's greeting")
	@ApiResponseObject(clazz = RGreetingsDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="{id}/")
	@ResponseBody
	public ResponseEntity<RGreetingsDTO> get(@ApiPathParam @PathVariable("id") String codedid) {
		return ResponseEntity.ok(service.findOne(codedid));
	}

	@ApiMethod(description="Search chapters' greetings using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="search/")
	@ResponseBody
	public ResponseEntity<List<RGreetingsDTO>> search(@QuerydslPredicate(root = Greetings.class) Predicate predicate) {
		return ResponseEntity.ok(service.findAll(predicate));
	}

	@ApiMethod(description="Search chapters' greetings using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="search/page/")
	@ResponseBody
	public ResponseEntity<Page<RGreetingsDTO>> search(@ApiPathParam @QuerydslPredicate(root = Greetings.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(predicate, pageable));
	}

	@ApiMethod(description="Create a new greeting")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@ApiBodyObject @RequestBody @Valid CGreetingsDTO dto) {
		service.save(dto);
		return ResponseEntity.ok(null);
	}

	@ApiMethod(description="Update a greeting")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UGreetingsDTO dto) {
		service.update(dto);
		return ResponseEntity.ok(null);
	}
}
