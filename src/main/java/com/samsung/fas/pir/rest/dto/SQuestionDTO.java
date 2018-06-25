package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(SQuestion.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SQuestionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 		UUID					id;

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
	private 		List<SAlternativeDTO>	alternatives;

	@Getter
	@Setter
	@JsonProperty("answers")
	private 		List<SAnswerDTO>		answers;

	public SQuestionDTO() {
		super();
	}

	public SQuestionDTO(SQuestion entity, Device device, boolean detailed) {
		setId(entity.getUuid());
		setDescription(entity.getDescription());
		setType(entity.getType());
		setValueType(entity.getValueType());
		setRequired(entity.isRequired());
		setAlternatives(entity.getAlternatives().stream().map(alternative -> new SAlternativeDTO(alternative, device, false)).collect(Collectors.toList()));
		setAnswers(detailed? entity.getAnswers().stream().map(answer -> new SAnswerDTO(answer, device, false)).collect(Collectors.toList()) : null);
	}

	@JsonIgnore
	public SQuestion getModel() {
		SQuestion model = new SQuestion();
		model.setUuid(getId());
		model.setDescription(getDescription());
		model.setType(getType());
		model.setRequired(isRequired());
		model.setValueType(getValueType());
		model.setAlternatives(getAlternatives() != null? getAlternatives().stream().map(item -> {
			SAlternative alternative = item.getModel();
			alternative.setQuestion(model);
			return alternative;
		}).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}
