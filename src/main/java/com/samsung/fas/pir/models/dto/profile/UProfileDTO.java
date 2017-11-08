package com.samsung.fas.pir.models.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Profile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

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
		profile.setGuid(UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(id), StandardCharsets.UTF_8)));
		profile.setTitle(title);
		profile.setActive(active);
		profile.setDescription(description);
		return profile;
	}
}
