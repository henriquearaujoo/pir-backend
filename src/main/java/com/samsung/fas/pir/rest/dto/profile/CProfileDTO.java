package com.samsung.fas.pir.rest.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/*
 * Create Profiles
 */

@ApiObject
public class CProfileDTO {
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

	public CProfileDTO() {
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
}
