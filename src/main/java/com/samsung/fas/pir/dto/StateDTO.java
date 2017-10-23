package com.samsung.fas.pir.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.State;

import lombok.Getter;

public class StateDTO {
	@Getter
	@JsonProperty("id")
	private		long			id;
	
	@Getter
	@JsonProperty("name")
	private		String			name;
	
	@Getter
	@JsonProperty("uf")
	private		String			uf;
	
	private StateDTO(State entity) {
		id			= entity.getId();
		name		= entity.getName();
		uf			= entity.getAbbreviation();
	}
	
	public static StateDTO toDTO(State entity) {
		if (entity != null) {
			return new StateDTO(entity);
		}
		return null;
	}
}
