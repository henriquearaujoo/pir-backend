package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.ResponsibleService;
import com.samsung.fas.pir.rest.services.base.BService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping(value = "/rest/responsibles", produces = MediaType.APPLICATION_JSON)
@Api(value = "Responsibles", description = "REST Controller for Responsibles", tags = "RESPONSIBLES")
public class ResponsibleController extends BController<Responsible, ResponsibleDTO, ResponsibleDAO> {
	@Autowired
	public ResponsibleController(BService<Responsible, ResponsibleDTO, ResponsibleDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother")
	@ResponseBody
	public ResponseEntity<?> findAllResponsbiles() {
		return ResponseEntity.ok(((ResponsibleService) service).findAllResponsible());
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/page")
	@ResponseBody
	public ResponseEntity<?> findAllResponsbiles(Pageable pageable) {
		return ResponseEntity.ok(((ResponsibleService) service).findAllResponsible(pageable));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/search")
	@ResponseBody
	public ResponseEntity<?> findAllResponsbiles(@QuerydslPredicate(root = Responsible.class) Predicate predicate) {
		return ResponseEntity.ok(((ResponsibleService) service).findAllResponsible(predicate));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/no-mother/search/page")
	@ResponseBody
	public ResponseEntity<?> findAllResponsbiles(@QuerydslPredicate(root = Responsible.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(((ResponsibleService) service).findAllResponsible(pageable, predicate));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search")
	@ResponseBody
	public ResponseEntity<?> search(@QuerydslPredicate(root = Responsible.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	@ResponseBody
	public ResponseEntity<?> search(@QuerydslPredicate(root = Responsible.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}
