package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.GreetingsDAO;
import com.samsung.fas.pir.persistence.models.Greetings;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.GreetingsDTO;
import com.samsung.fas.pir.rest.services.GreetingsBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.Collection;

@Api(value = "Greetings", description = "REST Controller for Chapter Greetings", tags = "CHAPTER GREETINGS")
@RequestMapping(value = "/rest/chapters/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GreetingsController extends BController<GreetingsBO, GreetingsDTO> {
	@Autowired
	public GreetingsController(GreetingsBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<GreetingsDTO>> search(@QuerydslPredicate(root = Greetings.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<GreetingsDTO>> search(@QuerydslPredicate(root = Greetings.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, details));
	}
}
