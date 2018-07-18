package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.models.Agent;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import javax.validation.constraints.NotNull;
import java.util.Date;

@DTO(Agent.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentDTO extends BaseDTO<Agent> {
	@Getter
	@Setter
	@JsonProperty(value = "code", access = JsonProperty.Access.READ_ONLY)
	private 	String					code;

	@Getter
	@Setter
	@JsonProperty("gender")
	private 	EGender 				gender;

	@Getter
	@Setter
	@JsonProperty("birth")
	private 	Date 					birth;

	@Getter
	@Setter
	@JsonProperty("phone")
	private 	String					phone;

	@Getter
	@Setter
	@JsonProperty("is_phone_owner")
	private 	boolean					phoneOwner;

	@Getter
	@Setter
	@JsonProperty("latitude")
	private 	Double					latitude;

	@Getter
	@Setter
	@JsonProperty("longitude")
	private 	Double					longitude;

	// region Relations
	@Getter
	@Setter
	@JsonProperty(value = "person", access = JsonProperty.Access.READ_ONLY)
	private 	PersonDTO				personDTO;

	@Getter
	@Setter
	@JsonProperty(value = "unity")
	@NotNull(message = "unity.missing")
	private 	ConservationUnityDTO	unityDTO;

	@Getter
	@Setter
	@JsonProperty(value = "city")
	@NotNull(message = "city.missing")
	private 	CityDTO					cityDTO;
	// endregion

	public AgentDTO() {
		super();
	}

	public AgentDTO(Agent agent, Device device, boolean detailed) {
		super(agent);
		setPersonDTO(new PersonDTO(agent.getPerson(), device, false));
	}

	@JsonIgnore
	@Override
	public Agent getModel() {
		Agent	agent	= super.getModel();
		agent.setUnity(getUnityDTO().getModel());
		agent.setCity(getCityDTO().getModel());
		return agent;
	}
}
