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
public class CAnswerDTO {
	@ApiObjectField(name="for_question", order=0)
	@JsonProperty("for_question")
	@NotBlank(message = "chapter.conclusion.question.id.missing")
	@Getter
	@Setter
	private 	String			questionID;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@NotBlank(message = "chapter.conclusion.question.answer.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@JsonIgnore
	public Answer getModel() {
		Answer 		a	= new Answer();
		Question q	= new Question();

		q.setId(IDCoder.decodeLong(questionID));
		a.setQuestion(q);
		a.setDescription(description);
		return a;
	}
}
