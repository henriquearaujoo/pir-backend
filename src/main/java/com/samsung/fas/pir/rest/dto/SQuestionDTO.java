package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SAlternative;
import com.samsung.fas.pir.persistence.models.SQuestion;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DTO(SQuestion.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SQuestionDTO extends BaseDTO<SQuestion> {
	@Getter
	@Setter
	@JsonProperty("required")
	private 		boolean					required;

	@Getter
	@Setter
	@JsonProperty("description")
	private			String					description;

	@Getter
	@Setter
	@JsonProperty("type")
	private 		String					type;

	@Getter
	@Setter
	@JsonProperty("value_type")
	private 		String					valueType;

	@Getter
	@Setter
	@JsonProperty("alternatives")
	private 		List<SAlternativeDTO>	alternativesDTO;

	@Getter
	@Setter
	@JsonProperty("answers")
	private 		List<SAnswerDTO>		answersDTO;

	public SQuestionDTO() {
		super();
	}

	public SQuestionDTO(SQuestion entity, Device device, boolean detailed) {
		super(entity);
		setAlternativesDTO(entity.getAlternatives().stream().map(alternative -> new SAlternativeDTO(alternative, device, false)).collect(Collectors.toList()));
		setAnswersDTO(detailed? entity.getAnswers().stream().map(answer -> new SAnswerDTO(answer, device, false)).collect(Collectors.toList()) : null);
	}

	@JsonIgnore
	@Override
	public SQuestion getModel() {
		SQuestion model = super.getModel();
		model.setAlternatives(getAlternativesDTO() != null? getAlternativesDTO().stream().map(item -> {
			SAlternative alternative = item.getModel();
			alternative.setQuestion(model);
			return alternative;
		}).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}