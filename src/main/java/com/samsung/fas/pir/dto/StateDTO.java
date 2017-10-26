package com.samsung.fas.pir.dto;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.State;

import lombok.Getter;

@ApiObject
public class StateDTO {
	@ApiObjectField(name="id")
	@Getter
	@JsonProperty("id")
	private		long			id;
	
	@ApiObjectField(name="name")
	@Getter
	@JsonProperty("name")
	private		String			name;
	
	@ApiObjectField(name="uf")
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
