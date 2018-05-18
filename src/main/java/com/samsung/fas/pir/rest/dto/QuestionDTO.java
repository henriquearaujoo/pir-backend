package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Question.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID			uuid;

	@Getter
	@Setter
	@JsonProperty("conclusion_id")
	private 	UUID			conclusionUUID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String			description;

	@Getter
	@Setter
	@JsonProperty("type")
	private		EAnswerType		type;

	@JsonProperty("alternatives")
	@Setter
	@Getter
	private		Collection<AlternativeDTO>	answers;

	public QuestionDTO() {
		super();
	}

	public QuestionDTO(Question question, boolean detailed) {
		setUuid(question.getUuid());
		setDescription(question.getDescription());
		setType(question.getType());
		setConclusionUUID(question.getConclusion().getUuid());
		setAnswers(question.getAlternatives() != null? question.getAlternatives().stream().map(item -> new AlternativeDTO(item, false)).collect(Collectors.toSet()) : null);
	}

	@JsonIgnore
	public Question getModel() {
		Question model = new Question();
		model.setUuid(getUuid());
		model.setDescription(getDescription());
		model.setType(getType());
		return model;
	}
}
