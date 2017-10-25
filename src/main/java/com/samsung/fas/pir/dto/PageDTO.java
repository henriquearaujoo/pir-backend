package com.samsung.fas.pir.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.Page;

public class PageDTO {
	@JsonProperty("id")
	private		UUID			id;
	@JsonProperty("title")
	private		String			title;
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
