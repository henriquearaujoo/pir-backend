package com.samsung.fas.pir.graph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
	@Getter
	@Setter
	@JsonProperty("maps")
	private		List<MapDTO>		mappings;

	@Getter
	@Setter
	@JsonProperty("table")
	private 	List<ColumnDTO>		table;

	public ResponseDTO() {
		super();
	}

	public ResponseDTO(List<MapDTO> mapping, List<ColumnDTO> table) {
		setMappings(mapping);
		setTable(table);
	}
}
