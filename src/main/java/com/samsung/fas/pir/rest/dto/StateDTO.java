package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.State;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		String				id;

	@Getter
	@Setter
	@JsonProperty("name")
	private		String				name;

	@Getter
	@Setter
	@JsonProperty("uf")
	private		String				uf;

	@Getter
	@Setter
	@JsonProperty("cities")
	private 	Collection<CityDTO>	cities;

	public StateDTO() {
		super();
	}

	public StateDTO(State state, boolean detailed) {
		setId(IDCoder.encode(state.getUuid()));
		setName(state.getName());
		setUf(state.getAbbreviation());
		setCities(state.getCities() != null && state.getCities().size() > 0 && detailed? state.getCities().stream().map(item -> new CityDTO(item, false)).collect(Collectors.toSet()) : null);
	}
}
