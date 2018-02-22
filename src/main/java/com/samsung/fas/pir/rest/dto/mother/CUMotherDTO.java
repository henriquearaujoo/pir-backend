package com.samsung.fas.pir.rest.dto.mother;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.persistence.models.enums.ECivilState;
import com.samsung.fas.pir.rest.dto.responsible.CUResponsibleDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CUMotherDTO extends CUResponsibleDTO {
	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String		name;

	@Getter
	@Setter
	@JsonProperty("children_count")
	@Min(value = 1, message = "invalid.count")
	private 	long		children;

	@Getter
	@Setter
	@JsonProperty("civil_state")
	@NotBlank(message = "civil.state.missing")
	private 	String		civilState;

	@JsonIgnore
	public Mother getModel() {
		Mother model = (Mother) super.getModel();
		model.setChildren(getChildren());
		model.setName(getName());
		model.setCivilState(ECivilState.valueOf(civilState));
		return model;
	}
}
