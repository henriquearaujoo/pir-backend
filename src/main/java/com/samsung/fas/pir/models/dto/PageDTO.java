package com.samsung.fas.pir.models.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Page;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDTO {
	@ApiObjectField(name="id", required=false)
	@JsonProperty("id")
	private		UUID			id;
	
	@ApiObjectField(name="title")
	@JsonProperty("title")
	private		String			title;
	
	@ApiObjectField(name="route")
	@JsonProperty("route")
	private		String			path;
	
	@ApiObjectField(name="rules")
	@JsonProperty("rules")
	private		List<RuleDTO>	rules;
	
	private PageDTO(Page entity, boolean complete) {
		id			= entity.getId();
		title		= entity.getTitle();
		path		= entity.getUrl();
		
		if (complete)
			rules	= entity.getRules().stream().map(m -> RuleDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public static PageDTO toDTO(Page entity, boolean complete) {
		if (entity != null) {
			return new PageDTO(entity, complete);
		}
		return null;
	}
}
