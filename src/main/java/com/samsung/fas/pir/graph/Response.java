package com.samsung.fas.pir.graph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	@Getter
	@Setter
	@JsonProperty("key")
	private		Object		key;

	@Getter
	@Setter
	@JsonProperty("value")
	private 	Object		value;

	public Response() {
		super();
	}

	public Response(Object key, Object value) {
		setKey(key);
		setValue(value);
	}
}
