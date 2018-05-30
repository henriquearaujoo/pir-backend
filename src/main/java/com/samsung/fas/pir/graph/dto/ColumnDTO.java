package com.samsung.fas.pir.graph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDTO {
	@Getter
	@Setter
	@JsonProperty("entity")
	private 	String			entity;

	@Getter
	@Setter
	@JsonProperty("property")
	private		String			column;

	@Getter
	@Setter
	@JsonProperty("values")
	private 	List<Object>	values;

	public ColumnDTO() {
		super();
	}

	public ColumnDTO(String entity, String column, List<Object> values) {
		setEntity(entity);
		setColumn(column);
		setValues(values);
	}
}
