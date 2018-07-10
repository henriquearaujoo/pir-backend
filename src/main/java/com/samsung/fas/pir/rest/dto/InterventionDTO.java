package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Intervention;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import java.util.UUID;

@DTO(Intervention.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterventionDTO extends BaseDTO<Intervention> {
	@Getter
	@Setter
	@JsonProperty("chapter_id")
	private		UUID		chapterUUID;

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

	public InterventionDTO(Intervention intervention, Device device, boolean detailed) {
		super(intervention);
		setChapterUUID(intervention.getChapter().getUuid());
	}
}
