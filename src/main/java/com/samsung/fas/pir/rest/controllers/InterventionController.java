package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.InterventionDAO;
import com.samsung.fas.pir.persistence.models.entity.Intervention;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.InterventionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.Collection;

@Api(value = "Chapter Intervention", description = "REST Controller for Chapter Intervention", tags = "CHAPTER INTERVENTION")
@Controller
@RequestMapping(value = "/rest/chapters/intervention", produces = MediaType.APPLICATION_JSON)
@ResponseBody
public class InterventionController extends BController<Intervention, InterventionDTO, InterventionDAO> {
	@Autowired
	public InterventionController(BService<Intervention, InterventionDTO, InterventionDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public ResponseEntity<Collection<InterventionDTO>> search(@QuerydslPredicate(root = Intervention.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/page")
	public ResponseEntity<Page<InterventionDTO>> search(@QuerydslPredicate(root = Intervention.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}