package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String 			id;

	@Getter
	@Setter
	@JsonProperty("title")
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
	@JsonProperty("created_at")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private 	Date 			createdAt;

	@Getter
	@Setter
	@JsonProperty("updated_at")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private 	Date			updatedAt;

	public ProfileDTO() {
		super();
	}

	public ProfileDTO(Profile profile, boolean detailed) {
		setId(IDCoder.encode(profile.getUuid()));
		setTitle(profile.getTitle());
		setDescription(profile.getDescription());
		setActive(profile.isActive());
		setCreatedAt(profile.getCreatedAt());
	}

	@JsonIgnore
	public Profile getModel() {
		Profile profile = new Profile();
		profile.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		profile.setTitle(getTitle());
		profile.setActive(isActive());
		profile.setDescription(getDescription());
		return profile;
	}

}
