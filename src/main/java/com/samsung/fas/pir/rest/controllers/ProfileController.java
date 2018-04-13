package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.ProfileDTO;
import com.samsung.fas.pir.rest.services.ProfileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;

@Api(value = "Profiles", description = "REST Controller for Profiles", tags = "PROFILES")
@RequestMapping(value = "/rest/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ProfileController extends BController<Profile, ProfileDTO, ProfileDAO> {
	@Autowired
	public ProfileController(ProfileService service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<ProfileDTO>> search(@QuerydslPredicate(root = Profile.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<ProfileDTO>> search(@QuerydslPredicate(root = Profile.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}