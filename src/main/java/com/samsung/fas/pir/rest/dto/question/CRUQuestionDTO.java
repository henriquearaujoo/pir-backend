package com.samsung.fas.pir.rest.dto.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.persistence.models.enums.EQuestionType;
import com.samsung.fas.pir.rest.dto.answer.CRUAnswerDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CRUQuestionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		String						id;

	@Getter
	@Setter
	@JsonProperty("type")
	@NotNull(message = "type.missing")
	private 	EQuestionType 				type;

	@Getter
	@Setter
	@JsonProperty("conclusion_id")
	private 	String						conclusionID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String						description;

	@ApiObjectField(name="answers", order=4)
	@JsonProperty("answers")
	@Setter
	@Getter
	private		Collection<CRUAnswerDTO>	answers;

	public CRUQuestionDTO() {
		super();
	}

	public CRUQuestionDTO(Question question) {
		setId(IDCoder.encode(question.getUuid()));
		setType(question.getType());
		setDescription(question.getDescription());
		setConclusionID(IDCoder.encode(question.getConclusion().getUuid()));
		setAnswers(question.getAnswers() != null? question.getAnswers().stream().map(CRUAnswerDTO::new).collect(Collectors.toSet()) : null);
	}

	@JsonIgnore
	public Question getModel() {
		Question model = new Question();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setDescription(getDescription());
		model.setType(getType());
		return model;
	}
}
