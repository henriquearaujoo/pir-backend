package com.samsung.fas.pir.rest.dto.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.persistence.models.enums.EQuestionType;
import com.samsung.fas.pir.rest.dto.answer.RAnswerDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RQuestionDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Setter
	@Getter
	private		String			id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@Setter
	@Getter
	private 	String 			description;

	@ApiObjectField(name="type", order=2)
	@JsonProperty("type")
	@Setter
	@Getter
	private EQuestionType type;

	@ApiObjectField(name="for_conclusion", order=3)
	@JsonProperty("for_conclusion")
	@Setter
	@Getter
	private 	String			conclusion;

	@ApiObjectField(name="answers", order=4)
	@JsonProperty("answers")
	@Setter
	@Getter
	private 	Set<RAnswerDTO> answers;

	private RQuestionDTO(Question e) {
		setId(IDCoder.encode(e.getUuid()));
		setType(e.getType());
		setDescription(e.getDescription());
		setConclusion(IDCoder.encode(e.getConclusion().getUuid()));
		setAnswers(e.getAnswers() != null? e.getAnswers().stream().map(RAnswerDTO::toDTO).collect(Collectors.toSet()) : null);
	}

	public static RQuestionDTO toDTO(Question e) {
		if (e != null)
			return new RQuestionDTO(e);
		return null;
	}
}