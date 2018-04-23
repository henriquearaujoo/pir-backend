package com.samsung.fas.pir.graph;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.FormDTO;
import com.samsung.fas.pir.rest.services.FormBO;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;

@Api(value = "Graph", description = "REST Controller for Entity Graph", tags = "GRAPH")
@RequestMapping(value = "/rest/graph", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GraphController {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		Graph		graph;

	@Autowired
	public GraphController(Graph graph) {
		setGraph(graph);
	}

	@RequestMapping(method= RequestMethod.GET)
	public ResponseEntity<?> search(@ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getGraph().getGraph());
	}
}
