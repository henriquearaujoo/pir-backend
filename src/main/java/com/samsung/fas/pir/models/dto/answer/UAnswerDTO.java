package com.samsung.fas.pir.models.dto.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Answer;
import com.samsung.fas.pir.models.entity.Question;
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
	private		String			id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@NotBlank(message = "chapter.conclusion.question.answer.description.missing")
	@Getter
	@Setter
	private 	String			description;

//	@ApiObjectField(name="question", order=2)
//	@JsonProperty("question")
//	@NotBlank(message = "chapter.conclusion.question.id.missing")
//	@Getter
//	@Setter
//	private 	String			question;

	@JsonIgnore
	public Answer getModel() {
		Answer e = new Answer();
		e.setQuestion(new Question());
		e.setDescription(description);
//		e.getQuestion().setId(IDCoder.decodeLong(question));
		e.setId(IDCoder.decodeLong(id));
		return e;
	}
}
