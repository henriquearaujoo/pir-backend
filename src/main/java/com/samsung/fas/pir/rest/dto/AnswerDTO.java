package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Alternative;
import com.samsung.fas.pir.persistence.models.Answer;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.UUID;

@DTO(Answer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDTO extends BaseDTO<Answer> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long				mobileId;

	@Getter
	@Setter
	@JsonProperty("answer")
	private 	String				description;

	@Getter
	@Setter
	@JsonProperty(value = "question_id", access = JsonProperty.Access.WRITE_ONLY)
	private 	UUID				questionUUID;

	@Getter
	@Setter
	@JsonProperty(value = "alternative_id", access = JsonProperty.Access.WRITE_ONLY)
	private 	UUID				alternativeUUID;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter
	@JsonProperty(value = "alternative")
	private 	AlternativeDTO		alternativeDTO;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter
	@JsonProperty(value = "question")
	private 	QuestionDTO			questionDTO;

	public AnswerDTO() {
		super();
	}

	public AnswerDTO(Answer answer, Device device, boolean detailed) {
		super(answer);
		setQuestionDTO(answer.getQuestion() != null? new QuestionDTO(answer.getQuestion(), false) : null);
		setAlternativeDTO(answer.getAlternative() != null? new AlternativeDTO(answer.getAlternative(), device, false) : null);
	}

	@JsonIgnore
	@Override
	public Answer getModel() {
		Answer 			answer 			= super.getModel();
		Alternative		alternative		= new Alternative();
		Question		question		= new Question();
		alternative.setUuid(getAlternativeUUID());
		question.setUuid(getQuestionUUID());
		answer.setAlternative(alternative);
		answer.setQuestion(question);
		return answer;
	}
}