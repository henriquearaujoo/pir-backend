package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SQuestion;
import com.samsung.fas.pir.persistence.models.Survey;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@DTO(Survey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyDTO extends BaseDTO<Survey> {
	@Getter
	@Setter
	@JsonProperty("description")
	private			String					description;

	@Getter
	@Setter
	@JsonProperty("questions")
	private 		List<SQuestionDTO>		questionsDTO;

	public SurveyDTO() {
		super();
	}

	public SurveyDTO(Survey entity, Device device, boolean detailed) {
		super(entity);
		setQuestionsDTO(entity.getQuestions().stream().sorted(Comparator.comparingLong(Base::getId)).map(item -> new SQuestionDTO(item, device, false)).collect(Collectors.toList()));
	}

	@JsonIgnore
	@Override
	public Survey getModel() {
		Survey model = super.getModel();
		model.setQuestions(getQuestionsDTO() != null? getQuestionsDTO().stream().map(item -> {
			SQuestion questionModel = item.getModel();
			questionModel.setSurvey(model);
			return questionModel;
		}).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}