package com.samsung.fas.pir.models.dto.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Answer;
import com.samsung.fas.pir.models.entity.Question;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class CAnswerDTO {
	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@NotBlank(message = "chapter.conclusion.question.answer.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@JsonIgnore
	public Answer getModel() {
		Answer e = new Answer();
		e.setQuestion(new Question());
		e.setDescription(description);
		return e;
	}
}
