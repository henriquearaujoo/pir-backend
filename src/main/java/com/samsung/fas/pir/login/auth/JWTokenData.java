package com.samsung.fas.pir.login.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class JWTokenData {
	@Getter
	@Setter
	@JsonProperty(value = "uid")
	private 	String		userid;

	@Getter
	@Setter
	@JsonProperty(value = "unm")
	private 	String		username;

	@Getter
	@Setter
	@JsonProperty(value = "ulg")
	private 	String		userlogin;

	@Getter
	@Setter
	@JsonProperty(value = "pfl")
	private 	String		perfilID;

	@Getter
	@Setter
	@JsonProperty(value = "pnm")
	private 	String		perfil;
}
