package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.dto.answer.CAnswerDTO;
import com.samsung.fas.pir.models.dto.answer.RAnswerDTO;
import com.samsung.fas.pir.models.dto.answer.UAnswerDTO;
import com.samsung.fas.pir.models.entity.Answer;
import com.samsung.fas.pir.services.AnswerService;
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

@Api(name = "Answer Services", description = "Methods for managing conclusion answers", group = "Chapters", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/chapters/conclusion/question/answer")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT})
public class AnswerController {
	private AnswerService service;

	@Autowired
	public AnswerController(AnswerService service) {
		this.service = service;
	}

	@ApiMethod(description="Get all answers from database")
	@ApiResponseObject(clazz = RAnswerDTO.class)
	@RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RAnswerDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@ApiMethod(description="Get all answers from database (pageable)")
	@ApiResponseObject(clazz = RAnswerDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/page")
	@ResponseBody
	public ResponseEntity<Page<RAnswerDTO>> getAll(@ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@ApiMethod(description="Get specific answer")
	@ApiResponseObject(clazz = RAnswerDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RAnswerDTO> get(@ApiPathParam @PathVariable("id") String codedid) {
		return ResponseEntity.ok(service.findOne(codedid));
	}

	@ApiMethod(description="Delete specific answer")
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseBody
	public ResponseEntity delete(@ApiPathParam @PathVariable("id") String codedid) {
		service.delete(codedid);
		return ResponseEntity.ok(null);
	}

	@ApiMethod(description="Search answers using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<List<RAnswerDTO>> search(@QuerydslPredicate(root = Answer.class) Predicate predicate) {
		return ResponseEntity.ok(service.findAll(predicate));
	}

	@ApiMethod(description="Search answers using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<RAnswerDTO>> search(@ApiPathParam @QuerydslPredicate(root = Answer.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(predicate, pageable));
	}

	@ApiMethod(description="Create a new answer")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@ApiBodyObject @RequestBody @Valid CAnswerDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@ApiMethod(description="Update an answer")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UAnswerDTO dto) {
		return ResponseEntity.ok(service.update(dto));
	}
}
