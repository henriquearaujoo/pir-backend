package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Answer;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		String			id;

	@Getter
	@Setter
	@JsonProperty("question_id")
	private 	String			questionID;

	@Getter
	@Setter
	@JsonProperty("answer")
	@NotBlank(message = "answer.missing")
	private 	String			answer;

	public AnswerDTO() {
		super();
	}

	public AnswerDTO(Answer answer, boolean detailed) {
		setId(IDCoder.encode(answer.getUuid()));
		setAnswer(answer.getDescription());
		setQuestionID(IDCoder.encode(answer.getQuestion().getUuid()));
	}

	@JsonIgnore
	public Answer getModel() {
		Answer 		a	= new Answer();
		a.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		a.setDescription(getAnswer());
		return a;
	}
}
