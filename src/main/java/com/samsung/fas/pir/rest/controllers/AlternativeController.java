package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Alternative;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.AlternativeDTO;
import com.samsung.fas.pir.rest.services.AlternativeBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.UUID;

@Api(value = "Chapter Conclusion Question Alternatives", description = "REST Controller for Chapter Conclusion Question Alternatives", tags = "CHAPTER CONCLUSION QUESTION ALTERNATIVES")
@RequestMapping(value = "/rest/chapters/conclusion/question/alternative", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AlternativeController extends BController<AlternativeBO, AlternativeDTO> {
	@Autowired
	public AlternativeController(AlternativeBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<AlternativeDTO>> search(@QuerydslPredicate(root = Alternative.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<AlternativeDTO>> search(@QuerydslPredicate(root = Alternative.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, details));
	}

	@RequestMapping(method= RequestMethod.DELETE, value="/{id}")
	public ResponseEntity delete(@PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(getService().delete(codedid));
	}
}
