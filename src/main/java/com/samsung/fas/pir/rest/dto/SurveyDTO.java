package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Survey;
import com.samsung.fas.pir.persistence.models.SurveyQuestion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@DTO(Survey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyDTO {
	@Getter
	@Setter
	@JsonProperty("description")
	private			String						description;

	@Getter
	@Setter
	@JsonProperty("survey_questions")
	private 		List<SurveyQuestionDTO>		surveyQuestions;



	// TODO: getModel & Constructor
}
