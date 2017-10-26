package com.samsung.fas.pir.dto;

import java.util.UUID;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.Page;

@ApiObject
public class PageDTO {
	@ApiObjectField(name="id", required=false)
	@JsonProperty("id")
	private		UUID			id;
	
	@ApiObjectField(name="title")
	@JsonProperty("title")
	private		String			title;
	
	@ApiObjectField(name="endpoint")
	@JsonProperty("endpoint")
	private		String			path;
	
	private PageDTO(Page entity) {
		id			= entity.getId();
		title		= entity.getTitle();
		path		= entity.getUrl();
	}
	
	public static PageDTO toDTO(Page entity) {
		if (entity != null) {
			return new PageDTO(entity);
		}
		return null;
	}
}
