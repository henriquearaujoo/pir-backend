package com.samsung.fas.pir.rest.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.State;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRUStateDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		long			id;

	@Getter
	@Setter
	@JsonProperty("name")
	private		String			name;

	@Getter
	@Setter
	@JsonProperty("uf")
	private		String			uf;

	public CRUStateDTO() {
		super();
	}

	public CRUStateDTO(State state) {
		setId(state.getId());
		setName(state.getName());
		setUf(state.getAbbreviation());
	}
}
