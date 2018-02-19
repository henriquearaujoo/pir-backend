package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.rest.dto.chapter.CChapterDTO;
import com.samsung.fas.pir.rest.dto.chapter.RChapterDTO;
import com.samsung.fas.pir.rest.dto.chapter.UChapterDTO;
import com.samsung.fas.pir.rest.services.ChapterService;
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

@Api(name = "Chapters Services", description = "Methods for managing chapters", group = "Chapters", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/chapters")
@Produces(MediaType.APPLICATION_JSON)
public class ChapterController {
	private ChapterService cservice;

	@Autowired
	public ChapterController(ChapterService cservice) {
		this.cservice = cservice;
	}

	@ApiMethod(description="Get all chapters from database")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RChapterDTO>> getAll() {
		return ResponseEntity.ok(cservice.findAll());
	}

	@ApiMethod(description="Get all chapters from database (pageable)")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/page")
	@ResponseBody
	public ResponseEntity<Page<RChapterDTO>> getAll(@ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(cservice.findAll(pageable));
	}

	@ApiMethod(description="Get all active chapters")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/active")
	@ResponseBody
	public ResponseEntity<List<RChapterDTO>> getAllValid() {
		return ResponseEntity.ok(cservice.findAllValid());
	}

	@ApiMethod(description="Get all active chapters")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/active/search")
	@ResponseBody
	public ResponseEntity<List<RChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(cservice.findAllValid(predicate));
	}

	@ApiMethod(description="Get all active chapters (pageable / searchable)")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/active/search/page")
	@ResponseBody
	public ResponseEntity<Page<RChapterDTO>> getAllValid(@ApiPathParam @QuerydslPredicate(root = Chapter.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(cservice.findAllValid(pageable, predicate));
	}

	@ApiMethod(description="Get all inactive chapters")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/inactive")
	@ResponseBody
	public ResponseEntity<List<RChapterDTO>> getAllInvalid() {
		return ResponseEntity.ok(cservice.findAllInvalid());
	}

	@ApiMethod(description="Get all inactive chapters (pageable)")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/inactive/page")
	@ResponseBody
	public ResponseEntity<Page<RChapterDTO>> getAllInvalid(@ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(cservice.findAllInvalid(pageable));
	}

	@ApiMethod(description="Get all active chapters (pageable / searchable)")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/inactive/search/page")
	@ResponseBody
	public ResponseEntity<Page<RChapterDTO>> getAllInvalid(@ApiPathParam @QuerydslPredicate(root = Chapter.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(cservice.findAllInvalid(pageable, predicate));
	}

	@ApiMethod(description="Get all active chapters")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, path="/active/page")
	@ResponseBody
	public ResponseEntity<Page<RChapterDTO>> getAllValid(Pageable pageable) {
		return ResponseEntity.ok(cservice.findAllValid(pageable));
	}

	@ApiMethod(description="Get specific chapter")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RChapterDTO> get(@ApiPathParam @PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(cservice.findOne(codedid));
	}

	@ApiMethod(description="Search chapters using specified filters on url")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<List<RChapterDTO>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(cservice.findAll(predicate));
	}

	@ApiMethod(description="Search chapters using specified filters on url (Pageable)")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<RChapterDTO>> search(@ApiPathParam @QuerydslPredicate(root = Chapter.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(cservice.findAll(predicate, pageable));
	}

	@ApiMethod(description="Create a new chapter")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@ApiBodyObject @RequestBody @Valid CChapterDTO dto) {
		return ResponseEntity.ok(cservice.save(dto));
	}

	@ApiMethod(description="Update a chapter")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UChapterDTO dto) {
		return ResponseEntity.ok(cservice.update(dto));
	}
}
