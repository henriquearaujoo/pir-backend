package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Intervention;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterventionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("chapter_id")
	private		UUID		chapterdUUID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String		description;

	@Getter
	@Setter
	@JsonProperty("activity")
	@NotBlank(message = "activity.missing")
	private 	String		activity;

	public InterventionDTO() {
		super();
	}

	public InterventionDTO(Intervention intervention, boolean detailed) {
		setUuid(intervention.getUuid());
		setDescription(intervention.getDescription());
		setChapterdUUID(intervention.getChapter().getUuid());
		setActivity(intervention.getActivity());
	}

	@JsonIgnore
	public Intervention getModel() {
		Intervention model = new Intervention();
		model.setDescription(getDescription());
		model.setActivity(getActivity());
		model.setUuid(getUuid());
		return model;
	}
}
