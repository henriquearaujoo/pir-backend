package com.samsung.fas.pir.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"nodes", "visited"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {
	@Getter
	@Setter
	@JsonProperty(value = "table", access = JsonProperty.Access.READ_ONLY)
	private				String				table;

	@Getter
	@Setter
	@JsonProperty(value = "entity", access = JsonProperty.Access.READ_ONLY)
	private				String				entity;

	@Getter
	@Setter
	@JsonProperty(value = "properties", access = JsonProperty.Access.READ_ONLY)
	private				List<Property>		properties;

	@Getter
	@Setter
	@JsonProperty("nodes")
	private	transient	List<Node>			nodes;

	@Getter
	@Setter
	@JsonProperty("visited")
	private	transient	boolean				visited;

	public Node() {
		super();
	}

	public Node(String table, String entity) {
		setTable(table);
		setEntity(entity);
		setVisited(false);
	}

	public int getSize() {
		return properties != null? properties.size() : 0;
	}

	@Override
	public String toString() {
		return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(this);
	}
}