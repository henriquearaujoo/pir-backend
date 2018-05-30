package com.samsung.fas.pir.graph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"nodeDTOS"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeDTO {
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
	@JsonProperty(value = "alias", access = JsonProperty.Access.READ_ONLY)
	private				String				alias;

	@Getter
	@Setter
	@JsonProperty(value = "properties", access = JsonProperty.Access.READ_ONLY)
	private				List<PropertyDTO>		properties;

	@Getter
	@Setter
	@JsonProperty("nodeDTOS")
	private	transient	List<NodeDTO> nodeDTOS;

	public NodeDTO() {
		super();
	}

	public NodeDTO(String table, String entity, String alias) {
		setTable(table);
		setEntity(entity);
		setAlias(alias);
	}

	public int getSize() {
		return properties != null? properties.size() : 0;
	}

	@Override
	public String toString() {
		return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(this);
	}
}