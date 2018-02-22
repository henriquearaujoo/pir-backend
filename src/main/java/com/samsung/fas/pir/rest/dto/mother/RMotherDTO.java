package com.samsung.fas.pir.rest.dto.mother;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.rest.dto.responsible.RResponsibleDTO;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RMotherDTO extends RResponsibleDTO {
	@Getter
	@Setter
	@JsonProperty("name")
	private 	String		name;

	@Getter
	@Setter
	@JsonProperty("children_count")
	private 	long		children;

	@Getter
	@Setter
	@JsonProperty("civil_state")
	private 	String		civilState;

	public RMotherDTO() {
		super();
	}

	public RMotherDTO(Mother mother) {
		super(mother);
		setName(mother.getName());
		setChildren(mother.getChildren());
		setCivilState(mother.getCivilState().toString());
	}
}
