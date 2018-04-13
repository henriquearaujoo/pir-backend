package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.rest.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDTO {
	@Setter
	@Getter
	@JsonProperty("id")
	private		String			id;

	@Setter
	@Getter
	@JsonProperty("name")
	private		String			name;

	@Setter
	@Getter
	@JsonProperty("state")
	private StateDTO state;

	public CityDTO() {
		super();
	}
	
	public CityDTO(City city, boolean detailed) {
		setId(IDCoder.encode(city.getUuid()));
		setName(city.getName());
		setState(new StateDTO(city.getState(), false));

	}
}
