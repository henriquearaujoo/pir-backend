package com.samsung.fas.pir.configuration.security.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.persistence.models.Agent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID					uuid;

	@Getter
	@Setter
	@JsonProperty(value = "code", access = JsonProperty.Access.READ_ONLY)
	private 	String					code;

	public AgentDTO(Agent agent) {
		if (agent != null) {
			setUuid(agent.getUuid());
			setCode(agent.getCode());
		}
	}
}
