package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.StateDAO;
import com.samsung.fas.pir.persistence.models.State;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.StateDTO;
import com.samsung.fas.pir.rest.services.StateCityBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Api(value = "States", description = "REST Controller for States", tags = "STATES")
@RequestMapping(value = "/rest/states", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class StateController extends BController<StateCityBO, StateDTO> {
	@Autowired
	public StateController(StateCityBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/find-uf/{uf}")
	public ResponseEntity<Collection<StateDTO>> findUF(@PathVariable("uf") String uf, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(Collections.singletonList(getService().findByUF(uf.toUpperCase())));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<StateDTO>> search(@QuerydslPredicate(root = State.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<StateDTO>> search(@QuerydslPredicate(root = State.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, details));
	}
}
