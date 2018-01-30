package com.samsung.fas.pir.login.rest.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class AuthenticationDTO {
	@Getter
	@Setter
	@NotEmpty(message = "username.empty")
	private	String	username;

	@Getter
	@Setter
	@Size(min = 8, message = "password.short")
	@NotEmpty(message = "password.empty")
	private	String	password;
}