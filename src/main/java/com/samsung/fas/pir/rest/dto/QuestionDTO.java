package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.rest.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;
import java.util.Collection;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		String						id;

	@Getter
	@Setter
	@JsonProperty("conclusion_id")
	private 	String						conclusionID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String						description;

	@JsonProperty("answers")
	@Setter
	@Getter
	private		Collection<AnswerDTO>	answers;

	public QuestionDTO() {
		super();
	}

	public QuestionDTO(Question question, boolean detailed) {
		setId(IDCoder.encode(question.getUuid()));
		setDescription(question.getDescription());
		setConclusionID(IDCoder.encode(question.getConclusion().getUuid()));
		setAnswers(question.getAnswers() != null? question.getAnswers().stream().map(item -> new AnswerDTO(item, false)).collect(Collectors.toSet()) : null);
	}

	@JsonIgnore
	public Question getModel() {
		Question model = new Question();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setDescription(getDescription());
		return model;
	}
}
