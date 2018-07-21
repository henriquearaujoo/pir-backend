package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.FamilyDTO;
import com.samsung.fas.pir.rest.services.FamilyBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

@Api(value = "Family", description = "REST Controller for Family", tags = "FAMILY")
@RequestMapping(value = "/rest/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class FamilyController extends BController<FamilyBO, FamilyDTO> {
	@Autowired
	public FamilyController(FamilyBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Family.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Family.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, device, details));
	}
}
