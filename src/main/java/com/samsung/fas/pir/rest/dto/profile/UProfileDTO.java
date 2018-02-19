package com.samsung.fas.pir.rest.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
public class UProfileDTO {
	@ApiObjectField(name="id", order=0)
	@Getter
	@Setter
	@JsonProperty("id")
	@NotBlank(message = "profile.id.blank")
	@NotEmpty(message = "profile.id.missing")
	private 	String 			id;

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

	@JsonIgnore
	public Profile getModel() {
		Profile profile = new Profile();
		profile.setUuid(IDCoder.decode(getId()));
		profile.setTitle(getTitle());
		profile.setActive(isActive());
		profile.setDescription(getDescription());
		return profile;
	}
}
