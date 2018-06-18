package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.ResponsibleBO;
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

@Api(value = "Responsibles", description = "REST Controller for Responsibles", tags = "RESPONSIBLES")
@RequestMapping(value = "/rest/responsibles", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ResponsibleController extends BController<ResponsibleBO, ResponsibleDTO> {
	@Autowired
	public ResponsibleController(ResponsibleBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother")
	public ResponseEntity<?> findAllResponsbiles(Device device) {
		return ResponseEntity.ok(getService().findAllResponsible(device));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/page")
	public ResponseEntity<?> findAllResponsbiles(Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllResponsible(pageable, device));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/search")
	public ResponseEntity<?> findAllResponsbiles(@QuerydslPredicate(root = Responsible.class) Predicate predicate, Device device) {
		return ResponseEntity.ok(getService().findAllResponsible(predicate, device));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/search/page")
	public ResponseEntity<?> findAllResponsbiles(@QuerydslPredicate(root = Responsible.class) Predicate predicate, Pageable pageable, Device device) {
		return ResponseEntity.ok(getService().findAllResponsible(pageable, predicate, device));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Responsible.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Responsible.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, device, details));
	}
}
