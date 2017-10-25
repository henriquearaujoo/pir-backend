package com.samsung.fas.pir.dto;

import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.Profile;

import lombok.Getter;
import lombok.Setter;

/*
 * Create, Update Profiles
 */
public class ProfileDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID			id;
	
	@Getter
	@Setter
	@JsonProperty("title")
	@NotEmpty(message="profile.title.empty")
	@NotBlank(message="profile.title.blank")
	private		String			title;
	
	@Getter
	@Setter
	@JsonProperty("description")
	private		String			description;
	
	@Getter
	@Setter
	@JsonProperty("status")
	private		boolean			active;
	
	@Getter
	@Setter
	@JsonProperty("created_by")
	@NotNull(message="profile.createdby.null")
	private		UUID			createdBy;
	
	@Getter
	@Setter
	@JsonProperty("modified_by")
	@NotNull(message="profile.updatedby.null")
	private		UUID			modifiedBy;
	
	@Getter
	@JsonProperty("created_at")
	private		String			createdAt;
	
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
