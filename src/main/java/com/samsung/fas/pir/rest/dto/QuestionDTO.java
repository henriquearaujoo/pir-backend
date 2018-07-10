package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Question.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO extends BaseDTO<Question> {
	@Getter
	@Setter
	@JsonProperty("conclusion_id")
	private 	UUID					conclusionUUID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String					description;

	@Getter
	@Setter
	@JsonProperty("type")
	private		EAnswerType				type;

	@JsonProperty("alternatives")
	@Setter
	@Getter
	private 	List<AlternativeDTO>	alternativesDTO;

	public QuestionDTO() {
		super();
	}

	public QuestionDTO(Question question, boolean detailed) {
		super(question);
		setConclusionUUID(question.getConclusion().getUuid());
		setAlternativesDTO(question.getAlternatives() != null? question.getAlternatives().stream().sorted(Comparator.comparing(BaseID::getId)).map(item -> new AlternativeDTO(item, null, false)).collect(Collectors.toList()) : null);
	}
}
