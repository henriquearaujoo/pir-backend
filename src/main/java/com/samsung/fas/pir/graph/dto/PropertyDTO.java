package com.samsung.fas.pir.graph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyDTO {
	@Getter
	@Setter
	@JsonProperty("property")
	private 	String		property;

	@Getter
	@Setter
	@JsonProperty("type")
	private		String		type;

	@Getter
	@Setter
	@JsonProperty("alias")
	private 	String		alias;

	public PropertyDTO(String property, String type, String alias) {
		setProperty(property);
		setType(type);
		setAlias(alias);
	}
}
