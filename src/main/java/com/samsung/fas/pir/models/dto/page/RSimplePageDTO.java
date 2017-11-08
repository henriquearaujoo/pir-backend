package com.samsung.fas.pir.models.dto.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Page;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.util.Base64Utils;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RSimplePageDTO {
	@Getter
	@ApiObjectField(name="id")
	@JsonProperty("id")
	private		String			id;

	@Getter
	@ApiObjectField(name="title")
	@JsonProperty("title")
	private		String			title;

	@Getter
	@ApiObjectField(name="route")
	@JsonProperty("route")
	private		String			path;
	
	private RSimplePageDTO(Page entity) {
		id			= Base64Utils.encodeToUrlSafeString(entity.getGuid().toString().getBytes());
		title		= entity.getTitle();
		path		= entity.getUrl();
	}
	
	public static RSimplePageDTO toDTO(Page entity) {
		if (entity != null) {
			return new RSimplePageDTO(entity);
		}
		return null;
	}
}
