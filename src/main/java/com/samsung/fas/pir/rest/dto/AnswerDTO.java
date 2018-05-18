package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Answer;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@DTO(Answer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDTO {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long		tempID;

	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("answer")
	private 	String		answer;

	@Getter(onMethod = @__({@JsonIgnore}))
	@Setter
	@JsonProperty(value = "question_id", access = JsonProperty.Access.WRITE_ONLY)
	private 	UUID		questionUUID;

	@Getter(onMethod = @__({@JsonIgnore}))
	@Setter
	@JsonProperty(value = "alternative_id", access = JsonProperty.Access.WRITE_ONLY)
	private 	UUID		alternativeUUID;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter(onMethod = @__({@JsonIgnore}))
	@JsonProperty(value = "alternative")
	private 	AlternativeDTO		alternative;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter(onMethod = @__({@JsonIgnore}))
	@JsonProperty(value = "question")
	private 	QuestionDTO			question;

	public AnswerDTO() {
		super();
	}

	public AnswerDTO(Answer answer, boolean detailed) {
		setTempID(answer.getTempID());
		setUuid(answer.getUuid());
		setAnswer(answer.getDescription());
		setQuestion(answer.getQuestion() != null? new QuestionDTO(answer.getQuestion(), false) : null);
		setAlternative(answer.getAlternative() != null? new AlternativeDTO(answer.getAlternative(), false) : null);
	}

	@JsonIgnore
	public Answer getModel() {
		Answer answer = new Answer();
		answer.setDescription(getAnswer());
		answer.setUuid(getUuid());
		answer.setTempID(getTempID());
		return answer;
	}

}
