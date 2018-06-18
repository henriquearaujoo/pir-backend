package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Intervention;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.InterventionDTO;
import com.samsung.fas.pir.rest.services.InterventionBO;
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

@Api(value = "Chapter Intervention", description = "REST Controller for Chapter Intervention", tags = "CHAPTER INTERVENTION")
@RequestMapping(value = "/rest/chapters/intervention", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class InterventionController extends BController<InterventionBO, InterventionDTO> {
	@Autowired
	public InterventionController(InterventionBO service) {
		super(service);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public ResponseEntity<Collection<InterventionDTO>> search(@QuerydslPredicate(root = Intervention.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, device, details));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/page")
	public ResponseEntity<Page<InterventionDTO>> search(@QuerydslPredicate(root = Intervention.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, device, details));
	}
}