package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.profile.CRUProfileDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.jsondoc.core.annotation.ApiPathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Controller
@RequestMapping(value = "/rest/profiles", produces = MediaType.APPLICATION_JSON)
public class ProfileController extends BController<Profile, CRUProfileDTO, ProfileDAO> {
	@Autowired
	public ProfileController(BService<Profile, CRUProfileDTO, ProfileDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<Collection<CRUProfileDTO>> search(@QuerydslPredicate(root = Profile.class) Predicate predicate) {
		return ResponseEntity.ok(service.findAll(predicate));
	}

	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<CRUProfileDTO>> search(@ApiPathParam @QuerydslPredicate(root = Profile.class) Predicate predicate, @ApiPathParam Pageable pageable) {
		return ResponseEntity.ok(service.findAll(predicate, pageable));
	}
}