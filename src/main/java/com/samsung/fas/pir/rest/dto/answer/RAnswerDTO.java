package com.samsung.fas.pir.rest.dto.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Answer;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RAnswerDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="description", order=2)
	@JsonProperty("description")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="for_question", order=1)
	@JsonProperty("for_question")
	@Getter
	@Setter
	private 	String			question;

	private RAnswerDTO(Answer e) {
		setId(IDCoder.encode(e.getUuid()));
		setDescription(e.getDescription());
		setQuestion(IDCoder.encode(e.getQuestion().getUuid()));
	}

	public static RAnswerDTO toDTO(Answer e) {
		if (e != null)
			return new RAnswerDTO(e);
		return null;
	}
}
