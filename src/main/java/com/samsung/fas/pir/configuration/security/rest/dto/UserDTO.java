package com.samsung.fas.pir.configuration.security.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Setter
	@Getter
	@JsonProperty(value="name")
	private		String		name;

	@Setter
	@Getter
	@JsonProperty("email")
	private		String		email;

	public UserDTO(User user) {
		setUuid(user.getUuid());
		setName(user.getName());
		setEmail(user.getEmail());
	}
}
