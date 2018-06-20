package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SQuestion;
import com.samsung.fas.pir.persistence.models.Survey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Survey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 		UUID 					id;

	@Getter
	@Setter
	@JsonProperty("description")
	private			String					description;

	@Getter
	@Setter
	@JsonProperty("questions")
	private 		List<SQuestionDTO>		questions;

	public SurveyDTO() {
		super();
	}

	public SurveyDTO(Survey entity, Device device, boolean detailed) {
		setId(entity.getUuid());
		setDescription(entity.getDescription());
		setQuestions(entity.getQuestions().stream().map(item -> new SQuestionDTO(item, device, false)).collect(Collectors.toList()));
	}

	@JsonIgnore
	public Survey getModel() {
		Survey model = new Survey();
		model.setUuid(getId());
		model.setDescription(getDescription());
		model.setQuestions(getQuestions() != null? getQuestions().stream().map(item -> {
			SQuestion questionModel = item.getModel();
			questionModel.setSurvey(model);
			return questionModel;
		}).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}
