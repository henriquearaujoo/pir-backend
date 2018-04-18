package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.State;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("name")
	private		String		name;

	@Getter
	@Setter
	@JsonProperty("uf")
	private		String		uf;

	@Getter
	@Setter
	@JsonProperty("cities")
	private 	Collection<CityDTO>		cities;

	public StateDTO() {
		super();
	}

	public StateDTO(State state, boolean detailed) {
		setUuid(state.getUuid());
		setName(state.getName());
		setUf(state.getAbbreviation());
		setCities(state.getCities() != null && state.getCities().size() > 0 && detailed? state.getCities().stream().map(item -> new CityDTO(item, false)).collect(Collectors.toSet()) : null);
	}
}
