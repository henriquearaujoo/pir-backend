package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EProfileType;
import com.samsung.fas.pir.persistence.models.Profile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.UUID;

@DTO(Profile.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("title")
	@NotBlank(message="profile.title.blank")
	private		String		title;

	@Getter
	@Setter
	@JsonProperty("description")
	private		String		description;

	@Getter
	@Setter
	@JsonProperty("status")
	private		boolean		active;

	@Getter
	@Setter
	@JsonProperty("created_at")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private 	Date 		createdAt;

	@Getter
	@Setter
	@JsonProperty("updated_at")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private 	Date		updatedAt;

	@Getter
	@Setter
	@JsonProperty("type")
	private 	EProfileType	type;

	public ProfileDTO() {
		super();
	}

	public ProfileDTO(Profile profile, boolean detailed) {
		setUuid(profile.getUuid());
		setTitle(profile.getTitle());
		setDescription(profile.getDescription());
		setActive(profile.isActive());
		setCreatedAt(profile.getCreatedAt());
		setType(profile.getType());
	}

	@JsonIgnore
	public Profile getModel() {
		Profile profile = new Profile();
		profile.setUuid(getUuid());
		profile.setTitle(getTitle());
		profile.setActive(isActive());
		profile.setDescription(getDescription());
		profile.setType(getType());
		return profile;
	}
}
