package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.CommunityDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping(value = "/rest/communities", produces = MediaType.APPLICATION_JSON)
@Api(value = "Communities", description = "REST Controller for Communities", tags = "COMMUNITIES")
public class CommunityController extends BController<Community, CommunityDTO, CommunityDAO> {
	@Autowired
	public CommunityController(BService<Community, CommunityDTO, CommunityDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search")
	@ResponseBody
	public ResponseEntity<?> search(@QuerydslPredicate(root = Community.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	@ResponseBody
	public ResponseEntity<?> search(@QuerydslPredicate(root = Community.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}
