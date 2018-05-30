package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Page;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@DTO(Page.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("title")
	private		String		title;

	@Getter
	@Setter
	@JsonProperty("route")
	private		String		path;

	@Getter
	@Setter
	@JsonProperty("rules")
	private 	Collection<RuleDTO>	rules;

	public PageDTO() {
		super();
	}

	public PageDTO(Page entity, boolean detailed) {
		setUuid(entity.getUuid());
		setTitle(entity.getTitle());
		setPath(entity.getUrl());
	}
}
