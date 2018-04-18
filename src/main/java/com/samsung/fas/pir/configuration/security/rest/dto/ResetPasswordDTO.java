package com.samsung.fas.pir.configuration.security.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordDTO {
	@Getter
	@Setter
	@JsonProperty("token")
	@NotEmpty(message = "token.empty")
	private		String		token;

	@Getter
	@Setter
	@JsonProperty("password")
	@Size(min = 8, message = "password.short")
	@NotEmpty(message = "password.empty")
	private		String		password;
}
