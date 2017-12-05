package com.samsung.fas.pir.models.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.City;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/*
 * Class used to retrieve cities by state
 */
@ApiObject
public class CityDTO {
	@ApiObjectField(name="id")
	@Getter
	@JsonProperty("id")
	private		long			id;
	
	@ApiObjectField(name="name")
	@Getter
	@JsonProperty("name")
	private		String			name;
	
	@ApiObjectField(name="state_id")
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
