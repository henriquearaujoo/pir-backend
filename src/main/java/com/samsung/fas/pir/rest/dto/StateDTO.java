package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.State;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.Collection;
import java.util.stream.Collectors;

@DTO(State.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateDTO extends BaseDTO<State> {
	@Getter
	@Setter
	@JsonProperty("name")
	private		String		name;

	@Getter
	@Setter
	@JsonProperty("uf")
	private		String		abbreviation;

	@Getter
	@Setter
	@JsonProperty("cities")
	private 	Collection<CityDTO>		cities;

	public StateDTO() {
		super();
	}

	public StateDTO(State state, Device device, boolean detailed) {
		super(state);
		setCities(detailed && state.getCities() != null? state.getCities().stream().map(item -> new CityDTO(item, device, false)).sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName())).collect(Collectors.toList()) : null);
	}
}
