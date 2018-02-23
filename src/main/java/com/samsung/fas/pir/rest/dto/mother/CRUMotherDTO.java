package com.samsung.fas.pir.rest.dto.mother;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.persistence.models.enums.ECivilState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CRUMotherDTO {
	@Getter
	@Setter
	@JsonProperty("children_count")
	@Min(value = 0, message = "invalid.count")
	private 	long		childrenCount;

	@Getter
	@Setter
	@JsonProperty("civil_state")
	@NotBlank(message = "civil.state.missing")
	private 	String		civilState;


	public CRUMotherDTO(Mother mother) {
		setChildrenCount(mother.getChildrenCount());
		setCivilState(mother.getCivilState().toString());
	}

	@JsonIgnore
	public Mother getModel() {
		Mother model = new Mother();
		model.setChildrenCount(getChildrenCount());
		model.setCivilState(ECivilState.valueOf(civilState));
		return model;
	}
}
