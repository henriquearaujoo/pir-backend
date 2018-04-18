package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Answer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDTO {
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
	@JsonProperty(value = "child_id", access = JsonProperty.Access.WRITE_ONLY)
	private 	UUID		childUUID;

	@Getter(onMethod = @__({@JsonIgnore}))
	@Setter
	@JsonProperty(value = "mother_id", access = JsonProperty.Access.WRITE_ONLY)
	private 	UUID		motherUUID;

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

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter(onMethod = @__({@JsonIgnore}))
	@JsonProperty(value = "child")
	private 	ChildDTO			child;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter(onMethod = @__({@JsonIgnore}))
	@JsonProperty(value = "mother")
	private 	MotherDTO			mother;

	public AnswerDTO() {
		super();
	}

	public AnswerDTO(Answer answer, boolean detailed) {
		setUuid(answer.getUuid());
		setAnswer(answer.getDescription());
		setChild(answer.getChild() != null? new ChildDTO(answer.getChild(), false) : null);
		setMother(answer.getMother() != null? new MotherDTO(answer.getMother(), false) : null);
		setQuestion(answer.getQuestion() != null? new QuestionDTO(answer.getQuestion(), false) : null);
		setAlternative(answer.getAlternative() != null? new AlternativeDTO(answer.getAlternative(), false) : null);
	}

	@JsonIgnore
	public Answer getModel() {
		Answer answer = new Answer();
		answer.setDescription(getAnswer());
		answer.setUuid(getUuid());
		return answer;
	}

}
