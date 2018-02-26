package com.samsung.fas.pir.rest.dto.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RProfileDTO {
	@ApiObjectField(name="id", order=0)
	@Getter
	@Setter
	@JsonProperty("id")
	private String id;

	@ApiObjectField(name="title", order=1)
	@Getter
	@Setter
	@JsonProperty("title")
	private		String			title;

	@ApiObjectField(name="description", order=2)
	@Getter
	@Setter
	@JsonProperty("description")
	private		String			description;

	@ApiObjectField(name="status", order=3)
	@Getter
	@Setter
	@JsonProperty("status")
	private		boolean			active;

	@ApiObjectField(name="created_at", order=6)
	@Getter
	@Setter
	@JsonProperty("created_at")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private		Date			createdAt;

	@ApiObjectField(name="updated_at", order=7)
	@Getter
	@Setter
	@JsonProperty("updated_at")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private 	Date			updatedAt;

	private RProfileDTO(Profile entity) {
		setId(IDCoder.encode(entity.getUuid()));
		setTitle(entity.getTitle());
		setDescription(entity.getDescription());
		setActive(entity.isActive());
		setCreatedAt(entity.getCreatedAt());
		setUpdatedAt(entity.getUpdatedAt());
	}

	public static RProfileDTO toDTO(Profile entity) {
		if (entity != null) {
			return new RProfileDTO(entity);
		}
		return null;
	}
}
