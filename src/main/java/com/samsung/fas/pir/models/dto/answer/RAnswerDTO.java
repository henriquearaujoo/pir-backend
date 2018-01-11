package com.samsung.fas.pir.models.dto.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Answer;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RAnswerDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	private		String			id;

	@ApiObjectField(name="description", order=2)
	@JsonProperty("description")
	@Getter
	private 	String			description;

	@ApiObjectField(name="for_question", order=1)
	@JsonProperty("for_question")
	@Getter
	private 	String			question;

	private RAnswerDTO(Answer e) {
		this.id				= IDCoder.encode(e.getId());
		this.description	= e.getDescription();
		this.question		= IDCoder.encode(e.getQuestion().getId());
	}

	public static RAnswerDTO toDTO(Answer e) {
		if (e != null)
			return new RAnswerDTO(e);
		return null;
	}
}
