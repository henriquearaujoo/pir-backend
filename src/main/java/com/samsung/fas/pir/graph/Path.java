package com.samsung.fas.pir.graph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Path {
	@Getter
	@Setter
	@JsonProperty("entity")
	private 	String			entity;

	@Getter
	@Setter
	@JsonProperty("grouped")
	private		List<String>	grouped;

	@Getter
	@Setter
	@JsonProperty("joins")
	private 	List<Path>		joins;
}
