package com.samsung.fas.pir.models.dto.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.answer.UAnswerDTO;
import com.samsung.fas.pir.models.entity.Conclusion;
import com.samsung.fas.pir.models.entity.Question;
import com.samsung.fas.pir.models.enums.EQuestionType;
import com.samsung.fas.pir.utils.IDCoder;
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
public class UQuestionDTO {
	@ApiObjectField(name="id", order=0, required = true)
	@JsonProperty("id")
	@NotNull(message = "chapter.conclusion.question.id.missing")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="type", order=1, required = true)
	@JsonProperty("type")
	@NotNull(message = "chapter.conclusion.question.type.missing")
	@Getter
	@Setter
	private 	EQuestionType 	type;

	@ApiObjectField(name="description", order=2, required = true)
	@JsonProperty("description")
	@NotBlank(message = "chapter.conclusion.questions.description.missing")
	@Getter
	@Setter
	private 	String			description;

//	@ApiObjectField(name="conclusion", order=3, required = true)
//	@JsonProperty("conclusion")
//	@NotNull(message = "chapter.conclusion.id.missing")
//	@Getter
//	@Setter
//	private 	String			conclusion;

	@ApiObjectField(name="answers", order=4)
	@JsonProperty("answers")
	@NotNull(message = "chapter.conclusion.questions.answer.missing")
	@Valid
	@Getter
	@Setter
	private 	Set<UAnswerDTO>	answers;

	@JsonIgnore
	public Question getModel() {
		Question e = new Question();
		e.setConclusion(new Conclusion());
		e.setDescription(description);
		e.setType(type);
		e.setId(IDCoder.decodeLong(id));
		e.setAnswers(answers.stream().map(UAnswerDTO::getModel).collect(Collectors.toCollection(HashSet::new)));
		e.getAnswers().forEach(item -> item.setQuestion(e));
		return e;
	}
}
