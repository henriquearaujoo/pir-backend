package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
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
	public ResponseEntity<?> findAllResponsbiles() {
		return ResponseEntity.ok(getService().findAllResponsible());
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/page")
	public ResponseEntity<?> findAllResponsbiles(Pageable pageable) {
		return ResponseEntity.ok(getService().findAllResponsible(pageable));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/search")
	public ResponseEntity<?> findAllResponsbiles(@QuerydslPredicate(root = Responsible.class) Predicate predicate) {
		return ResponseEntity.ok(getService().findAllResponsible(predicate));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/search/page")
	public ResponseEntity<?> findAllResponsbiles(@QuerydslPredicate(root = Responsible.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(getService().findAllResponsible(pageable, predicate));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Responsible.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Responsible.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, details));
	}
}
