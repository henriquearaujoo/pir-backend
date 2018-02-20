package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.community.CCommunityDTO;
import com.samsung.fas.pir.rest.dto.community.RCommunityDTO;
import com.samsung.fas.pir.rest.dto.community.UCommunityDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;

@Api(name = "Communities Services", description = "Methods for managing communities", group = "Child", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping(value = "/rest/community", produces = MediaType.APPLICATION_JSON)
public class CommunityController extends BController<Community, CCommunityDTO, RCommunityDTO, UCommunityDTO, CommunityDAO> {

	@Autowired
	public CommunityController(BService<Community, CCommunityDTO, RCommunityDTO, UCommunityDTO, CommunityDAO, Long> service) {
		super(service);
	}

	@ApiMethod(description="GET Data with QueryDSL Filters")
	@RequestMapping(method= RequestMethod.GET, path = "/search")
	@ResponseBody
	public ResponseEntity<?> search(@QuerydslPredicate(root = Community.class) Predicate predicate) {
		return ResponseEntity.ok(service.findAll(predicate));
	}

	@ApiMethod(description="GET Data with QueryDSL Filters (Pageable)")
	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	@ResponseBody
	public ResponseEntity<?> search(@QuerydslPredicate(root = Community.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(predicate, pageable));
	}
}
