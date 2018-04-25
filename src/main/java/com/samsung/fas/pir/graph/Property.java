package com.samsung.fas.pir.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class Property {
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

	public Property(String property, String type, String alias) {
		setProperty(property);
		setType(type);
		setAlias(alias);
	}
}
