package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Pregnancy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PregnancyDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID 		uuid;

	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long		tempID;

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
		setUuid(entity.getUuid());
		setTempID(entity.getMobileId());
		setMother(detailed? new MotherDTO(entity.getPregnant(), false) : null);
		setRegisteredAt(entity.getRegisteredAt());
		setAgent(entity.getAgent() != null? new UserDTO(entity.getAgent(), true) : null);
		setVisits(entity.getVisits() != null? entity.getVisits().stream().map(item -> new VisitDTO(item, false)).collect(Collectors.toList()) : new ArrayList<>());
	}

	@JsonIgnore
	public Pregnancy getModel() {
		Pregnancy model = new Pregnancy();
		model.setUuid(getUuid());
		model.setMobileId(getTempID());
		model.setRegisteredAt(getRegisteredAt());
		model.setVisits(getVisits() != null? getVisits().stream().map(VisitDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}