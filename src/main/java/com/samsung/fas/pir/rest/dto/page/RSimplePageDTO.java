package com.samsung.fas.pir.rest.dto.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RSimplePageDTO {
	@Getter
	@Setter
	@ApiObjectField(name="id")
	@JsonProperty("id")
	private		String			id;

	@Getter
	@Setter
	@ApiObjectField(name="title")
	@JsonProperty("title")
	private		String			title;

	@Getter
	@Setter
	@ApiObjectField(name="route")
	@JsonProperty("route")
	private		String			path;
	
	RSimplePageDTO(Page entity) {
		setId(IDCoder.encode(entity.getUuid()));
		setTitle(entity.getTitle());
		setPath(entity.getUrl());
	}
	
	public static RSimplePageDTO toDTO(Page entity) {
		if (entity != null) {
			return new RSimplePageDTO(entity);
		}
		return null;
	}
}
