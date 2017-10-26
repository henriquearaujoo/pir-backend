package com.samsung.fas.pir.models.dto;

import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Profile;

import lombok.Getter;
import lombok.Setter;

/*
 * Create, Update Profiles
 */
@ApiObject
public class ProfileDTO {
	@ApiObjectField(required=false, name="id", order=0)
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID			id;
	
	@ApiObjectField(name="title", order=1)
	@Getter
	@Setter
	@JsonProperty("title")
	@NotEmpty(message="profile.title.empty")
	@NotBlank(message="profile.title.blank")
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
	
	@ApiObjectField(name="created_by", order=4)
	@Getter
	@Setter
	@JsonProperty("created_by")
	@NotNull(message="profile.createdby.null")
	private		UUID			createdBy;
	
	@ApiObjectField(name="modified_by", order=5)
	@Getter
	@Setter
	@JsonProperty("modified_by")
	@NotNull(message="profile.updatedby.null")
	private		UUID			modifiedBy;
	
	@ApiObjectField(name="created_at", order=6)
	@Getter
	@JsonProperty("created_at")
	private		String			createdAt;
	
	@ApiObjectField(name="updated_at", order=7)
	@Getter
	@JsonProperty("updated_at")
	private		String			updatedAt;
	
	private ProfileDTO(Profile entity) {
		id				= entity.getId();
		title			= entity.getTitle();
		description		= entity.getDescription();
		active			= entity.isActive();
		createdAt		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(entity.getCreatedAt());
		updatedAt		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(entity.getUpdatedAt());
		modifiedBy		= entity.getWhoUpdated().getId();
		createdBy		= entity.getWhoCreated().getId();
	}
	
	public ProfileDTO() {
		// JSON
	}
	
	@JsonIgnore
	public Profile getModel() {
		Profile profile = new Profile();
		profile.setTitle(title);
		profile.setActive(active);
		profile.setDescription(description);
		return profile;
	}
	
	public static ProfileDTO toDTO(Profile entity) {
		if (entity != null) {
			return new ProfileDTO(entity);
		}
		return null;
	}
	
	public static Profile toModel(ProfileDTO dto) {
		if (dto != null) {
			Profile profile = new Profile();
			profile.setActive(dto.active);
			profile.setTitle(dto.getTitle());
			profile.setDescription(dto.description);
			return profile;
		}
		return null;
	}
}
