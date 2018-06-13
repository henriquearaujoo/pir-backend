package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Mother;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DTO(Mother.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MotherDTO {
	@Getter
	@Setter
	@JsonProperty("responsible")
	private 	ResponsibleDTO			responsible;

	@Getter
	@Setter
	@JsonProperty("is_pregnant")
	private		boolean					pregnant;

	@Getter
	@Setter
	@JsonProperty("pregnancies")
	private 	List<PregnancyDTO> 		pregnancies;

	@Getter
	@Setter
	@JsonProperty("children")
	private 	List<ChildDTO> 			children;

	public MotherDTO() {
		super();
	}

	public MotherDTO(Mother mother, boolean detailed) {
		setPregnant(mother.isPregnant());
		setPregnancies(mother.getPregnancies().stream().map(item -> new PregnancyDTO(item, true)).collect(Collectors.toList()));
		setChildren(mother.getChildren().stream().map(item -> new ChildDTO(item, false)).collect(Collectors.toList()));
		setResponsible(detailed? new ResponsibleDTO(mother.getResponsible(), false) : null);
	}

	@JsonIgnore
	public Mother getModel() {
		Mother model = new Mother();
		model.setPregnancies(pregnancies != null? pregnancies.stream().map(PregnancyDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setChildren(getChildren() != null? getChildren().stream().map(ChildDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setPregnant(isPregnant());
		return model;
	}
}
