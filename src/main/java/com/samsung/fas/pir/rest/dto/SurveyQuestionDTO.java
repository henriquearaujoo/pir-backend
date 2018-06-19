package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SAnswer;
import com.samsung.fas.pir.persistence.models.Survey;
import com.samsung.fas.pir.persistence.models.SurveyQuestion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@DTO(SurveyQuestion.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyQuestionDTO {
	@Getter
	@Setter
	@JsonProperty("survey")
	private			SurveyDTO			survey;

	@Getter
	@Setter
	@JsonProperty("question")
	private 		SQuestionDTO		question;

	@Getter
	@Setter
	@JsonProperty("answers")
	private 		List<SAnswerDTO>	answers;

	// TODO: getModel & Constructor
}
