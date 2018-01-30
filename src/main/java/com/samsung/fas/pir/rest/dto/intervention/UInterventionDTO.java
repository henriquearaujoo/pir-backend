package com.samsung.fas.pir.rest.dto.intervention;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Intervention;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class UInterventionDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@NotBlank(message = "chapter.intervention.id.missing")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="chapter", order=1)
	@JsonProperty("chapter")
	@NotBlank(message = "chapter.intervention.chapter.missing")
	@Getter
	@Setter
	private		String			chapterdID;

	@ApiObjectField(name="descritpion", order=2)
	@JsonProperty("description")
	@NotBlank(message = "chapter.intervention.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="activity", order=3)
	@JsonProperty("activity")
	@NotBlank(message = "chapter.intervention.activity.missing")
	@Getter
	@Setter
	private 	String			activity;

	@JsonIgnore
	public Intervention getModel() {
		Intervention e = new Intervention();
		e.setChapter(new Chapter());
		e.setDescription(description);
		e.setActivity(activity);
		e.setId(IDCoder.decodeLong(id));
		e.getChapter().setId(IDCoder.decodeLong(chapterdID));
		return e;
	}
}
