package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

@DTO(City.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDTO extends BaseDTO<City> {
	@Setter
	@Getter
	@JsonProperty("name")
	private		String		name;

	@Setter
	@Getter
	@JsonProperty("state")
	private 	StateDTO	stateDTO;

	public CityDTO() {
		super();
	}
	
	public CityDTO(City city, Device device, boolean detailed) {
		super(city);
		setStateDTO(new StateDTO(city.getState(), device, false));
	}
}
