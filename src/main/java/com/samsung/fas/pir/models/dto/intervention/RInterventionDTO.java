package com.samsung.fas.pir.models.dto.intervention;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
}