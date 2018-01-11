package com.samsung.fas.pir.models.dto.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.answer.RAnswerDTO;
import com.samsung.fas.pir.models.entity.Answer;
import com.samsung.fas.pir.models.entity.Question;
import com.samsung.fas.pir.models.enums.EQuestionType;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Set;
import java.util.stream.Collectors;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RQuestionDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	private		String			id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@Getter
	private 	String 			description;

	@ApiObjectField(name="type", order=2)
	@JsonProperty("type")
	@Getter
	private 	EQuestionType 	type;

	@ApiObjectField(name="for_conclusion", order=3)
	@JsonProperty("for_conclusion")
	@Getter
	private 	String			conclusion;

	@ApiObjectField(name="answers", order=4)
	@JsonProperty("answers")
	@Getter
	private 	Set<RAnswerDTO> answers;

	private RQuestionDTO(Question e) {
		Set<Answer>		answers	= e.getAnswers();
		this.id					= IDCoder.encode(e.getId());
		this.type				= e.getType();
		this.description		= e.getDescription();
		this.conclusion			= IDCoder.encode(e.getConclusion().getId());
		if (answers != null && !answers.isEmpty())
			this.answers		= answers.stream().map(RAnswerDTO::toDTO).collect(Collectors.toSet());
	}

	public static RQuestionDTO toDTO(Question e) {
		if (e != null)
			return new RQuestionDTO(e);
		return null;
	}
}