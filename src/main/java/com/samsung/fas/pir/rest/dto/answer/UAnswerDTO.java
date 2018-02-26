package com.samsung.fas.pir.rest.dto.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Answer;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class UAnswerDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@NotBlank(message = "chapter.conclusion.question.answer.id.missing")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="description", order=2)
	@JsonProperty("description")
	@NotBlank(message = "chapter.conclusion.question.answer.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="for_question", order=1)
	@JsonProperty("for_question")
	@NotBlank(message = "chapter.conclusion.question.id.missing")
	@Getter
	@Setter
	private 	String			questionID;

	@JsonIgnore
	public Answer getModel() {
		Answer 		a	= new Answer();
		Question 	q	= new Question();

		q.setUuid(IDCoder.decode(getQuestionID()));
		a.setQuestion(q);
		a.setDescription(getDescription());
		a.setUuid(IDCoder.decode(getId()));
		return a;
	}
}
