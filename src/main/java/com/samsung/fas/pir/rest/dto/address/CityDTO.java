package com.samsung.fas.pir.rest.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.City;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
