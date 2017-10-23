package com.samsung.fas.pir.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.City;

import lombok.Getter;

/*
 * Class used to retrieve cities by state
 */
public class CityDTO {
	@Getter
	@JsonProperty("id")
	private		long			id;
	
	@Getter
	@JsonProperty("name")
	private		String			name;
	
	@Getter
	@JsonProperty("state_id")
	private		long			stateId;
	
	private CityDTO(City embedded) {
		id			= embedded.getId();
		name		= embedded.getName();
		stateId		= embedded.getState().getId();
	}
	
	public static CityDTO toDTO(City entity) {
		if (entity != null) {
			return new CityDTO(entity);
		}
		return null;
	}
}
