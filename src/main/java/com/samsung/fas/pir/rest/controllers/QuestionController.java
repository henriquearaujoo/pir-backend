package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.rest.dto.question.CQuestionDTO;
import com.samsung.fas.pir.rest.dto.question.RQuestionDTO;
import com.samsung.fas.pir.rest.dto.question.UQuestionDTO;
import com.samsung.fas.pir.rest.services.QuestionService;
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

@Api(name = "Question Services", description = "Methods for managing conclusion questions", group = "Chapters", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/chapters/conclusion/question")
@Produces(MediaType.APPLICATION_JSON)
public class QuestionController {
	private QuestionService service;

	@Autowired
	public QuestionController(QuestionService service) {
		this.service = service;
	}

	@ApiMethod(description="Get all questions from database")
	@ApiResponseObject(clazz = RQuestionDTO.class)
	@RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RQuestionDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@ApiMethod(description="Get all questions from database (pageable)")
	@ApiResponseObject(clazz = RQuestionDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/page")
	@ResponseBody
	public ResponseEntity<Page<RQuestionDTO>> getAll(@ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@ApiMethod(description="Get specific question")
	@ApiResponseObject(clazz = RQuestionDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RQuestionDTO> get(@ApiPathParam @PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(service.findOne(codedid));
	}

	@ApiMethod(description="Search questions using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<List<RQuestionDTO>> search(@QuerydslPredicate(root = Question.class) Predicate predicate) {
		return ResponseEntity.ok(service.findAll(predicate));
	}

	@ApiMethod(description="Search questions using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<RQuestionDTO>> search(@ApiPathParam @QuerydslPredicate(root = Question.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(predicate, pageable));
	}

	@ApiMethod(description="Delete specific answer")
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseBody
	public ResponseEntity delete(@ApiPathParam @PathVariable("id") UUID codedid) {
		service.delete(codedid);
		return ResponseEntity.ok(null);
	}

	@ApiMethod(description="Create a new question")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@ApiBodyObject @RequestBody @Valid CQuestionDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@ApiMethod(description="Update a question")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UQuestionDTO dto) {
		return ResponseEntity.ok(service.update(dto));
	}
}
