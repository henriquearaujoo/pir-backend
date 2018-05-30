package com.samsung.fas.pir.graph;

import com.samsung.fas.pir.graph.dto.PathDTO;
import com.samsung.fas.pir.graph.dto.MapDTO;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Api(value = "Graph", description = "REST Controller for Entity Graph", tags = "GRAPH")
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GraphController {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		Graph		graph;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		Query		query;

	@Autowired
	public GraphController(Graph graph, Query query) {
		setGraph(graph);
		setQuery(query);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/graph")
	public ResponseEntity<?> graph(@ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getGraph().getGraph());
	}

	@RequestMapping(method= RequestMethod.POST, path = "/query")
	public ResponseEntity<?> query(@RequestBody @Valid PathDTO root, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getQuery().query(root));
	}
}
