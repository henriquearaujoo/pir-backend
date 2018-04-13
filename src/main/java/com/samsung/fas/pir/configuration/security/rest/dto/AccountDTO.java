package com.samsung.fas.pir.configuration.security.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import lombok.Getter;
import lombok.Setter;

@JsonTypeName("acc")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
	@Getter
	@Setter
	@JsonProperty("username")
	private		String		username;

	@Getter
	@Setter
	@JsonProperty("user")
	private 	UserDTO 	user;

	@Getter
	@Setter
	@JsonProperty("profile")
	private 	ProfileDTO 	profile;

	public AccountDTO(Account account) {
		setUsername(account.getUsername());
		setUser(new UserDTO(account.getUser()));
		setProfile(new ProfileDTO(account.getProfile()));
	}
}