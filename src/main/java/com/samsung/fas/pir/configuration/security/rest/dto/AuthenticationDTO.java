package com.samsung.fas.pir.configuration.security.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationDTO {
	@Getter
	@Setter
	@NotEmpty(message = "username.empty")
	private		String		username;

	@Getter
	@Setter
	@Size(min = 8, message = "password.short")
	@NotEmpty(message = "password.empty")
	private		String		password;
}