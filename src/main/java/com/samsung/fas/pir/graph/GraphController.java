package com.samsung.fas.pir.graph;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<?, ?>	query 		= getQuery().query(root);
		Map<?, ?>	response	= new HashMap<>();

		query.entrySet().forEach(entry -> {
			Class<?>	keyClass;
			Class<?>	valueClass;

			if (entry.getKey() != null) {
				keyClass = entry.getKey().getClass();//Query.findClass("com.samsung.fas.pir", entry.getKey().getClass().getSimpleName() + "DTO", null);
				System.out.println("KEY Class: " + keyClass.getSimpleName());
			}

			if (entry.getValue() != null) {
				valueClass = ((List) entry.getValue()).iterator().next().getClass();
				System.out.println("\tVAL Class: " + valueClass.getSimpleName());
			}

		});

		return ResponseEntity.ok(null);
	}
}
