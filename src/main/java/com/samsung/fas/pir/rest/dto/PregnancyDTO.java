package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@DTO(Pregnancy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PregnancyDTO {
	@Getter
	@Setter
	@JsonProperty("mother")
	private		MotherDTO		mother;

	@Getter
	@Setter
	@JsonProperty("registered_at")
	private 	Date			registeredAt;

	@Getter
	@Setter
	@JsonProperty("agent")
	private 	UserDTO			agent;

	@Getter
	@Setter
	@JsonProperty("visits")
	private		List<VisitDTO>	visits;

	public PregnancyDTO() {
		super();
	}

	public PregnancyDTO(Pregnancy entity, boolean detailed) {
		setMother(detailed? new MotherDTO(entity.getPregnant(), false) : null);
		setRegisteredAt(entity.getRegisteredAt());
		setAgent(new UserDTO(entity.getAgent(), true));
		setVisits(entity.getVisits() != null? entity.getVisits().stream().map(item -> new VisitDTO(item, true)).collect(Collectors.toList()) : new ArrayList<>());
	}

	public Pregnancy getModel() {
		Pregnancy model = new Pregnancy();
		model.setAgent(getAgent().getModel());
		model.setPregnant(getMother().getModel());
		model.setRegisteredAt(getRegisteredAt());
		model.setVisits(getVisits() != null? getVisits().stream().map(VisitDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}
