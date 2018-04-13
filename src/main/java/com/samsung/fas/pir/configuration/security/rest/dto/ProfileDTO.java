package com.samsung.fas.pir.configuration.security.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.configuration.security.persistence.models.Authority;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.rest.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String 				id;

	@Getter
	@Setter
	@JsonProperty("title")
	private		String				title;

	@Getter
	@Setter
	@JsonProperty("description")
	private		String				description;

	@Getter
	@Setter
	@JsonProperty("authorities")
	private 	Collection<String>	authorities;

	public ProfileDTO(Profile profile) {
		setId(IDCoder.encode(profile.getUuid()));
		setTitle(profile.getTitle());
		setDescription(profile.getDescription());
		setAuthorities(profile.getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toList()));
	}
}
