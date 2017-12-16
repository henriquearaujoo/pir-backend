package com.samsung.fas.pir.models.dto.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.answer.CAnswerDTO;
import com.samsung.fas.pir.models.entity.Conclusion;
import com.samsung.fas.pir.models.entity.Question;
import com.samsung.fas.pir.models.enums.EQuestionType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class CQuestionDTO {
	@ApiObjectField(name="type", order=1)
	@JsonProperty("type")
	@NotNull(message = "chapter.conclusion.question.type.missing")
	@Getter
	@Setter
	private 	EQuestionType 	type;

	@ApiObjectField(name="description", order=3, required = true)
	@JsonProperty("description")
	@NotBlank(message = "chapter.conclusion.questions.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="answers", order=4)
	@JsonProperty("answers")
	@NotNull(message = "chapter.conclusion.questions.answer.missing")
	@Valid
	@Getter
	@Setter
	private 	Set<CAnswerDTO>	answers;

	@JsonIgnore
	public Question getModel() {
		Question e = new Question();
		e.setConclusion(new Conclusion());
		e.setDescription(description);
		e.setType(type);
		e.setAnswers(answers.stream().map(CAnswerDTO::getModel).collect(Collectors.toCollection(HashSet::new)));
		e.getAnswers().forEach(item -> item.setQuestion(e));
		return e;
	}
}
