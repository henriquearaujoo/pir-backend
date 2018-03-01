package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.chapter.CRUChapterDTO;
import com.samsung.fas.pir.rest.services.ChapterService;
import com.samsung.fas.pir.rest.services.base.BService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Controller
@RequestMapping(value = "/rest/chapters", produces = MediaType.APPLICATION_JSON)
@Api(value = "Chapters", description = "REST Controller for Chapters", tags = "CHAPTERS")
public class ChapterController extends BController<Chapter, CRUChapterDTO, ChapterDAO> {
	@Autowired
	public ChapterController(BService<Chapter, CRUChapterDTO, ChapterDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method=RequestMethod.GET, path="/active")
	@ResponseBody
	public ResponseEntity<Collection<CRUChapterDTO>> getAllValid() {
		return ResponseEntity.ok(((ChapterService) service).findAllValid());
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/search")
	@ResponseBody
	public ResponseEntity<Collection<CRUChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(((ChapterService) service).findAllValid(predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/search/page")
	@ResponseBody
	public ResponseEntity<Page<CRUChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllValid(pageable, predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive")
	@ResponseBody
	public ResponseEntity<Collection<CRUChapterDTO>> getAllInvalid() {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalid());
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive/page")
	@ResponseBody
	public ResponseEntity<Page<CRUChapterDTO>> getAllInvalid(Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalid(pageable));
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive/search/page")
	@ResponseBody
	public ResponseEntity<Page<CRUChapterDTO>> getAllInvalid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalid(pageable, predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/page")
	@ResponseBody
	public ResponseEntity<Page<CRUChapterDTO>> getAllValid(Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllValid(pageable));
	}

	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<Collection<CRUChapterDTO>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<CRUChapterDTO>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}
