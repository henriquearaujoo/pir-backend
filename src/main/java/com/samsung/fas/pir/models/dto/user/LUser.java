package com.samsung.fas.pir.models.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;

@ApiObject
public class LUser {
	@ApiObjectField(name="login", order=2)
	@Setter
	@Getter
	@JsonProperty("login")
	@NotEmpty(message="user.login.empty")
	@NotBlank(message="user.login.blank")
	private		String			login;

	@ApiObjectField(name="password", order=3)
	@Setter
	@Getter
	@JsonProperty("password")
	@Size(min=8, message="user.password.short")
	private		String			password;
}
