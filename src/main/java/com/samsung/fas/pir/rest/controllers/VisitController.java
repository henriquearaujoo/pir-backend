package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Visit;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.VisitDTO;
import com.samsung.fas.pir.rest.services.VisitBO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.UUID;

@Api(value = "Visit", description = "REST Controller for Visit", tags = "VISIT")
@RequestMapping(value = "/rest/visit", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class VisitController extends BController<VisitBO, VisitDTO> {
	@Autowired
	public VisitController(VisitBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/front/{id}")
	public ResponseEntity<?> findOneDetailed(@PathVariable("id") UUID id, @ApiIgnore @AuthenticationPrincipal UserDetails details, Device device) {
		return ResponseEntity.ok(getService().findOneDetailed(id, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/front")
	public ResponseEntity<?> findAllDetailed(@ApiIgnore @AuthenticationPrincipal UserDetails details, Device device) {
		return ResponseEntity.ok(getService().findAll(device, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/front/page")
	public ResponseEntity<?> findAllDetailed(Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAllDetailed(pageable, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/front/search")
	public ResponseEntity<?> findAllDeteailed(@QuerydslPredicate(root = Visit.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAllDetailed(predicate, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/front/search/page")
	public ResponseEntity<?> findAllDeteailed(@QuerydslPredicate(root = Visit.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAllDetailed(predicate, pageable, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<VisitDTO>> search(@QuerydslPredicate(root = Visit.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<VisitDTO>> search(@QuerydslPredicate(root = Visit.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, device, details));
	}
}
