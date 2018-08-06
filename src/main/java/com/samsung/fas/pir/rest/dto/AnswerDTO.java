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
	@JsonProperty(value = "question_id")
	private 	UUID				questionUUID;

	@Getter
	@Setter
	@JsonProperty(value = "alternative_id")
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
		if (device.isNormal()) {
			setQuestionDTO(answer.getQuestion() != null ? new QuestionDTO(answer.getQuestion(), false) : null);
			setAlternativeDTO(answer.getAlternative() != null ? new AlternativeDTO(answer.getAlternative(), device, false) : null);
		} else {
			setQuestionUUID(answer.getQuestion().getUuid());
			setAlternativeUUID(answer.getAlternative() != null? answer.getAlternative().getUuid() : null);
		}
	}

	@JsonIgnore
	@Override
	public Answer getModel() {
		Answer 			model 			= new Answer();
		Alternative		alternative		= new Alternative();
		Question		question		= new Question();

		model.setUuid(getUuid());
		model.setMobileId(getMobileId());
		model.setDescription(getDescription());

		alternative.setUuid(getAlternativeUUID());
		question.setUuid(getQuestionUUID());
		model.setAlternative(alternative);
		model.setQuestion(question);
		return model;
	}
}