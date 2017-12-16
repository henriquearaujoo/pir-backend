package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.dto.chapter.CChapterDTO;
import com.samsung.fas.pir.models.dto.chapter.RChapterDTO;
import com.samsung.fas.pir.models.dto.chapter.UChapterDTO;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.services.ChapterService;
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

@Api(name = "Chapters Services", description = "Methods for managing chapters", group = "Chapters", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/chapters")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT})
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

	@ApiMethod(description="Get specific chapter")
	@ApiResponseObject(clazz = RChapterDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<RChapterDTO> get(@ApiPathParam @PathVariable("id") String codedid) {
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
		cservice.save(dto);
		return ResponseEntity.ok(null);
	}

	@ApiMethod(description="Update a chapter")
	@ApiResponseObject
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid UChapterDTO dto) {
		return ResponseEntity.ok(cservice.update(dto));
	}
}
