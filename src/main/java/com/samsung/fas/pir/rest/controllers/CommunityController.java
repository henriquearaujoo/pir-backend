package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.CommunityDTO;
import com.samsung.fas.pir.rest.services.CommunityBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collection;

@Api(value = "Communities", description = "REST Controller for Communities", tags = "COMMUNITIES")
@RequestMapping(value = "/rest/communities", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CommunityController extends BController<CommunityBO, CommunityDTO> {
	@Autowired
	public CommunityController(CommunityBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Community.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Community.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, details));
	}
}
