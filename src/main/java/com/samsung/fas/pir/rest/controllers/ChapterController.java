package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.ChapterDTO;
import com.samsung.fas.pir.rest.dto.ChapterDetailedDTO;
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
public class ChapterController extends BController<Chapter, ChapterDTO, ChapterDAO> {
	@Autowired
	public ChapterController(BService<Chapter, ChapterDTO, ChapterDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method=RequestMethod.GET, path="/active")
	@ResponseBody
	public ResponseEntity<Collection<ChapterDTO>> getAllValid() {
		return ResponseEntity.ok(((ChapterService) service).findAllValid());
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/detailed")
	@ResponseBody
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllValidDetailed() {
		return ResponseEntity.ok(((ChapterService) service).findAllValidDetailed());
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/detailed/search")
	@ResponseBody
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllValidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(((ChapterService) service).findAllValidDetailed(predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/detailed/search/page")
	@ResponseBody
	public ResponseEntity<Page<ChapterDetailedDTO>> getAllValidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllValidDetailed(predicate, pageable));
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive/detailed")
	@ResponseBody
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllInvalidDetailed() {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalidDetailed());
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive/detailed/search")
	@ResponseBody
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllInvalidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalidDetailed(predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive/detailed/search/page")
	@ResponseBody
	public ResponseEntity<Page<ChapterDetailedDTO>> getAllInvalidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalidDetailed(predicate, pageable));
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/search")
	@ResponseBody
	public ResponseEntity<Collection<ChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(((ChapterService) service).findAllValid(predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/search/page")
	@ResponseBody
	public ResponseEntity<Page<ChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllValid(pageable, predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive")
	@ResponseBody
	public ResponseEntity<Collection<ChapterDTO>> getAllInvalid() {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalid());
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive/page")
	@ResponseBody
	public ResponseEntity<Page<ChapterDTO>> getAllInvalid(Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalid(pageable));
	}

	@RequestMapping(method=RequestMethod.GET, path="/inactive/search/page")
	@ResponseBody
	public ResponseEntity<Page<ChapterDTO>> getAllInvalid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllInvalid(pageable, predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/active/page")
	@ResponseBody
	public ResponseEntity<Page<ChapterDTO>> getAllValid(Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllValid(pageable));
	}

	@RequestMapping(method=RequestMethod.GET, path="/detailed/page")
	@ResponseBody
	public ResponseEntity<Page<ChapterDetailedDTO>> getAllDetailed(Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllDetailed(pageable));
	}

	@RequestMapping(method=RequestMethod.GET, path="/detailed/search")
	@ResponseBody
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(((ChapterService) service).findAllDetailed(predicate));
	}

	@RequestMapping(method=RequestMethod.GET, path="/detailed/search/page")
	@ResponseBody
	public ResponseEntity<Page<ChapterDetailedDTO>> getAllDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(((ChapterService) service).findAllDetailed(predicate, pageable));
	}

	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<Collection<?>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<?>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}
