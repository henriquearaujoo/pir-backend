package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Mother;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@DTO(Mother.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MotherDTO {
	@Getter
	@Setter
	@JsonProperty("is_pregnant")
	private		boolean					pregnant;

	@Getter
	@Setter
	@JsonProperty("pregnancies")
	private 	List<PregnancyDTO> 		pregnancies;

	public MotherDTO() {
		super();
	}

	public MotherDTO(Mother mother, boolean detailed) {
		// TODO
	}

	@JsonIgnore
	public Mother getModel() {
		Mother model = new Mother();

		return model;
	}
}
