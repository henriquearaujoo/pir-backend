package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.ChapterDTO;
import com.samsung.fas.pir.rest.services.ChapterBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
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

//	@Cacheable(cacheNames = "chapter_active")
	@RequestMapping(method= RequestMethod.GET, path="/active")
	public ResponseEntity<Collection<ChapterDTO>> getAllValid(Device device) {
		return ResponseEntity.ok(getService().findAllValid(device));
	}

//	@Cacheable(cacheNames = "chapter_active_detailed")
	@RequestMapping(method= RequestMethod.GET, path="/active/detailed")
	public ResponseEntity<Collection<ChapterDTO>> getAllValidDetailed(Device device) {
		return ResponseEntity.ok(getService().findAllValidDetailed(device));
	}

//	@Cacheable(cacheNames = "chapter_active_detailed_search")
	@RequestMapping(method= RequestMethod.GET, path="/active/detailed/search")
	public ResponseEntity<Collection<ChapterDTO>> getAllValidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Device device) {
		return ResponseEntity.ok(getService().findAllValidDetailed(predicate, device));
	}

//	@Cacheable(cacheNames = "chapter_active_detailed_search_page")
	@RequestMapping(method= RequestMethod.GET, path="/active/detailed/search/page")
	public ResponseEntity<Page<ChapterDTO>> getAllValidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllValidDetailed(predicate, pageable, device));
	}

//	@Cacheable(cacheNames = "chapter_inactive_detailed")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/detailed")
	public ResponseEntity<Collection<ChapterDTO>> getAllInvalidDetailed(Device device) {
		return ResponseEntity.ok(getService().findAllInvalidDetailed(device));
	}

//	@Cacheable(cacheNames = "chapter_inactive_detailed_search")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/detailed/search")
	public ResponseEntity<Collection<ChapterDTO>> getAllInvalidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Device device) {
		return ResponseEntity.ok(getService().findAllInvalidDetailed(predicate, device));
	}

//	@Cacheable(cacheNames = "chapter_inactive_detailed_page_search")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/detailed/search/page")
	public ResponseEntity<Page<ChapterDTO>> getAllInvalidDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllInvalidDetailed(predicate, pageable, device));
	}

//	@Cacheable(cacheNames = "chapter_active_search")
	@RequestMapping(method= RequestMethod.GET, path="/active/search")
	public ResponseEntity<Collection<ChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Device device) {
		return ResponseEntity.ok(getService().findAllValid(predicate, device));
	}

//	@Cacheable(cacheNames = "chapter_active_page_search")
	@RequestMapping(method= RequestMethod.GET, path="/active/search/page")
	public ResponseEntity<Page<ChapterDTO>> getAllValid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllValid(pageable, predicate, device));
	}

//	@Cacheable(cacheNames = "chapter_inactive")
	@RequestMapping(method= RequestMethod.GET, path="/inactive")
	public ResponseEntity<Collection<ChapterDTO>> getAllInvalid(Device device) {
		return ResponseEntity.ok(getService().findAllInvalid(device));
	}

//	@Cacheable(cacheNames = "chapter_inactive_page")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/page")
	public ResponseEntity<Page<ChapterDTO>> getAllInvalid(Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllInvalid(pageable, device));
	}

//	@Cacheable(cacheNames = "chapter_inactive_page_search")
	@RequestMapping(method= RequestMethod.GET, path="/inactive/search/page")
	public ResponseEntity<Page<ChapterDTO>> getAllInvalid(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllInvalid(pageable, predicate, device));
	}

//	@Cacheable(cacheNames = "chapter_active_page")
	@RequestMapping(method= RequestMethod.GET, path="/active/page")
	public ResponseEntity<Page<ChapterDTO>> getAllValid(Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllValid(pageable, device));
	}

//	@Cacheable(cacheNames = "chapter_detailed_page")
	@RequestMapping(method= RequestMethod.GET, path="/detailed/page")
	public ResponseEntity<Page<ChapterDTO>> getAllDetailed(Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllDetailed(pageable, device));
	}

//	@Cacheable(cacheNames = "chapter_detailed_search")
	@RequestMapping(method= RequestMethod.GET, path="/detailed/search")
	public ResponseEntity<Collection<ChapterDTO>> getAllDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Device device) {
		return ResponseEntity.ok(getService().findAllDetailed(predicate, device));
	}

//	@Cacheable(cacheNames = "chapter_detailed_page")
	@RequestMapping(method= RequestMethod.GET, path="/detailed/search/page")
	public ResponseEntity<Page<ChapterDTO>> getAllDetailed(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllDetailed(predicate, pageable, device));
	}

//	@Cacheable(cacheNames = "chapter_search")
	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<?>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, device, details));
	}

//	@Cacheable(cacheNames = "chapter_page")
	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<?>> search(@QuerydslPredicate(root = Chapter.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, device, details));
	}
}
