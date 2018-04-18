package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.ChapterDTO;
import com.samsung.fas.pir.rest.dto.ChapterDetailedDTO;
import com.samsung.fas.pir.rest.services.ChapterBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;

@Api(value = "Chapters", description = "REST Controller for Chapters", tags = "CHAPTERS")
@RequestMapping(value = "/rest/chapters", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ChapterController extends BController<ChapterBO, ChapterDTO> {
	@Autowired
	public ChapterController(ChapterBO service) {
		super(service);
	}

	@Cacheable(cacheNames = "chapter_active")
	@RequestMapping(method= RequestMethod.GET, path="/active")
	public ResponseEntity<Collection<ChapterDTO>> getAllValid() {
		return ResponseEntity.ok(getService().findAllValid());
	}

	@Cacheable(cacheNames = "chapter_active_detailed")
	@RequestMapping(method= RequestMethod.GET, path="/active/detailed")
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllValidDetailed() {
		return ResponseEntity.ok(getService().findAllValidDetailed());
	}

	@Cacheable(cacheNames = "chapter_active_detailed_search")
	@RequestMapping(method= RequestMethod.GET, path="/active/detailed/search")
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllValidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(getService().findAllValidDetailed(predicate));
	}

	@Cacheable(cacheNames = "chapter_active_detailed_search_page")
	@RequestMapping(method= RequestMethod.GET, path="/active/detailed/search/page")
	public ResponseEntity<Page<ChapterDetailedDTO>> getAllValidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(getService().findAllValidDetailed(predicate, pageable));
	}

	@Cacheable(cacheNames = "chapter_inactive_detailed")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/detailed")
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllInvalidDetailed() {
		return ResponseEntity.ok(getService().findAllInvalidDetailed());
	}

	@Cacheable(cacheNames = "chapter_inactive_detailed_search")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/detailed/search")
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllInvalidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(getService().findAllInvalidDetailed(predicate));
	}

	@Cacheable(cacheNames = "chapter_inactive_detailed_page_search")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/detailed/search/page")
	public ResponseEntity<Page<ChapterDetailedDTO>> getAllInvalidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(getService().findAllInvalidDetailed(predicate, pageable));
	}

	@Cacheable(cacheNames = "chapter_active_search")
	@RequestMapping(method= RequestMethod.GET, path="/active/search")
	public ResponseEntity<Collection<ChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(getService().findAllValid(predicate));
	}

	@Cacheable(cacheNames = "chapter_active_page_search")
	@RequestMapping(method= RequestMethod.GET, path="/active/search/page")
	public ResponseEntity<Page<ChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(getService().findAllValid(pageable, predicate));
	}

	@Cacheable(cacheNames = "chapter_inactive")
	@RequestMapping(method= RequestMethod.GET, path="/inactive")
	public ResponseEntity<Collection<ChapterDTO>> getAllInvalid() {
		return ResponseEntity.ok(getService().findAllInvalid());
	}

	@Cacheable(cacheNames = "chapter_inactive_page")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/page")
	public ResponseEntity<Page<ChapterDTO>> getAllInvalid(Pageable pageable) {
		return ResponseEntity.ok(getService().findAllInvalid(pageable));
	}

	@Cacheable(cacheNames = "chapter_inactive_page_search")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/search/page")
	public ResponseEntity<Page<ChapterDTO>> getAllInvalid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(getService().findAllInvalid(pageable, predicate));
	}

	@Cacheable(cacheNames = "chapter_active_page")
	@RequestMapping(method= RequestMethod.GET, path="/active/page")
	public ResponseEntity<Page<ChapterDTO>> getAllValid(Pageable pageable) {
		return ResponseEntity.ok(getService().findAllValid(pageable));
	}

	@Cacheable(cacheNames = "chapter_detailed_page")
	@RequestMapping(method= RequestMethod.GET, path="/detailed/page")
	public ResponseEntity<Page<ChapterDetailedDTO>> getAllDetailed(Pageable pageable) {
		return ResponseEntity.ok(getService().findAllDetailed(pageable));
	}

	@Cacheable(cacheNames = "chapter_detailed_search")
	@RequestMapping(method= RequestMethod.GET, path="/detailed/search")
	public ResponseEntity<Collection<ChapterDetailedDTO>> getAllDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate) {
		return ResponseEntity.ok(getService().findAllDetailed(predicate));
	}

	@Cacheable(cacheNames = "chapter_detailed_page")
	@RequestMapping(method= RequestMethod.GET, path="/detailed/search/page")
	public ResponseEntity<Page<ChapterDetailedDTO>> getAllDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(getService().findAllDetailed(predicate, pageable));
	}

	@Cacheable(cacheNames = "chapter_search")
	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<?>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, details));
	}

	@Cacheable(cacheNames = "chapter_page")
	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<?>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, details));
	}
}
