package com.samsung.fas.pir.login.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;

import javax.validation.constraints.Size;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
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