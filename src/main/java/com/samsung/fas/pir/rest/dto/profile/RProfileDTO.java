package com.samsung.fas.pir.rest.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.util.Base64Utils;

import java.text.SimpleDateFormat;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RProfileDTO {
	@ApiObjectField(name="id", order=0)
	@Getter
	@JsonProperty("id")
	private String id;

	@ApiObjectField(name="title", order=1)
	@Getter
	@JsonProperty("title")
	private		String			title;

	@ApiObjectField(name="description", order=2)
	@Getter
	@JsonProperty("description")
	private		String			description;

	@ApiObjectField(name="status", order=3)
	@Getter
	@JsonProperty("status")
	private		boolean			active;

	@ApiObjectField(name="created_at", order=6)
	@Getter
	@JsonProperty("created_at")
	private		String			createdAt;

	@ApiObjectField(name="updated_at", order=7)
	@Getter
	@JsonProperty("updated_at")
	private		String			updatedAt;

	private RProfileDTO(Profile entity) {
		id				= Base64Utils.encodeToUrlSafeString(entity.getGuid().toString().getBytes());
		title			= entity.getTitle();
		description		= entity.getDescription();
		active			= entity.isActive();
		createdAt		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(entity.getCreatedAt());
		updatedAt		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(entity.getUpdatedAt());
	}

	public static RProfileDTO toDTO(Profile entity) {
		if (entity != null) {
			return new RProfileDTO(entity);
		}
		return null;
	}
}
