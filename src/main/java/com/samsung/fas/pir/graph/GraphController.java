package com.samsung.fas.pir.graph;

import com.google.gson.Gson;
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

@Api(value = "Graph", description = "REST Controller for Entity Graph", tags = "GRAPH")
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GraphController {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		Graph		graph;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		Query		query;

	@Autowired
	public GraphController(Graph graph) {
		setGraph(graph);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/graph")
	public ResponseEntity<?> graph(@ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getGraph().getGraph());
	}

	@RequestMapping(method= RequestMethod.POST, path = "/query")
	public ResponseEntity<?> query(@RequestBody @Valid Path root, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(new Gson().toJson(getQuery().query(root)));
	}
}
