package com.samsung.fas.pir.models.dto.intervention;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Intervention;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RInterventionDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	private		String			id;

	@ApiObjectField(name="descritpion", order=1)
	@JsonProperty("description")
	@Getter
	private 	String			description;

	@ApiObjectField(name="activity", order=2)
	@JsonProperty("activity")
	@Getter
	private 	String			activity;

	@ApiObjectField(name="chapter", order=3)
	@JsonProperty("chapter")
	@Getter
	private 	String			chapter;

	private RInterventionDTO(Intervention e) {
		this.id				= IDCoder.encode(e.getId());
		this.description	= e.getDescription();
		this.chapter		= IDCoder.encode(e.getChapter().getId());
		this.activity		= e.getActivity();
	}

	public static RInterventionDTO toDTO(Intervention e) {
		if (e != null)
			return new RInterventionDTO(e);
		return null;
	}
}