package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EProfileType;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@DTO(Profile.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDTO extends BaseDTO<Profile> {
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
	@JsonProperty("type")
	private 	EProfileType	type;

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

	public ProfileDTO() {
		super();
	}

	public ProfileDTO(Profile profile, boolean detailed) {
		super(profile);
	}
}