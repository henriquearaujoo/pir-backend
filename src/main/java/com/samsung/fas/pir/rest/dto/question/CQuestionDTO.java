package com.samsung.fas.pir.rest.dto.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.persistence.models.enums.EQuestionType;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.NotNull;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class CQuestionDTO {
	@ApiObjectField(name="type", order=1)
	@JsonProperty("type")
	@NotNull(message = "chapter.conclusion.question.type.missing")
	@Getter
	@Setter
	private EQuestionType type;

	@ApiObjectField(name="for_conclusion", order=2, required = true)
	@JsonProperty("for_conclusion")
	@NotBlank(message = "chapter.conclusion.id.missing")
	@Getter
	@Setter
	private 	String			conclusionID;

	@ApiObjectField(name="description", order=3, required = true)
	@JsonProperty("description")
	@NotBlank(message = "chapter.conclusion.questions.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@JsonIgnore
	public Question getModel() {
		Question 	q 	= new Question();
		Conclusion c	= new Conclusion();

		c.setId(IDCoder.decodeLong(conclusionID));
		q.setConclusion(new Conclusion());
		q.setDescription(description);
		q.setType(type);
		q.setConclusion(c);
		return q;
	}
}
