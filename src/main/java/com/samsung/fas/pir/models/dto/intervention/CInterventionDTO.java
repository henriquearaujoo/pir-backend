package com.samsung.fas.pir.models.dto.intervention;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Intervention;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class CInterventionDTO {
	@ApiObjectField(name="chapter", order=0)
	@JsonProperty("chapter")
	@NotBlank(message = "chapter.intervention.chapter.missing")
	@Getter
	@Setter
	private		String			chapterdID;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@NotBlank(message = "chapter.intervention.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="activity", order=2)
	@JsonProperty("activity")
	@NotBlank(message = "chapter.intervention.activity.missing")
	@Getter
	@Setter
	private 	String			activity;

	@JsonIgnore
	public Intervention getModel() {
		Intervention e = new Intervention();
		e.setDescription(description);
		e.setActivity(activity);
		return e;
	}
}
