package com.samsung.fas.pir.graph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PathDTO {
	@Getter
	@Setter
	@JsonProperty("entity")
	private 	String			entity;

	@Getter
	@Setter
	@JsonProperty("isLeaf")
	private 	boolean			group;

	@Getter
	@Setter
	@JsonProperty("joins")
	private 	List<PathDTO>	joins;
}
