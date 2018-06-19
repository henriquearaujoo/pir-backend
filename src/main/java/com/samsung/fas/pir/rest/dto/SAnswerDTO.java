package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SAlternative;
import com.samsung.fas.pir.persistence.models.SAnswer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.List;

@DTO(SAnswer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SAnswerDTO {
	@Getter
	@Setter
	@JsonProperty("description")
	private 	String				description;

	@Getter
	@Setter
	@JsonProperty("alternative")
	private		SAlternative		alternative;

	@Getter
	@Setter
	@JsonProperty("survey_question")
	private 	SurveyQuestionDTO	surveyQuestion;

	@Getter
	@Setter
	@JsonProperty("agent")
	private 	UserDTO				agent;

	@Getter
	@Setter
	@JsonProperty("child")
	private 	ChildDTO 			child;

	@Getter
	@Setter
	@JsonProperty("mother")
	private 	MotherDTO 			mother;

	public SAnswerDTO() {
		super();
	}

	public SAnswerDTO(SAnswer entity, Device device, boolean detailed) {
		setDescription(entity.getDescription());
//		setAlternative(new SAlternativeDTO(entity.getAlternative(), device, false));
//		setSurveyQuestion(new SurveyQuestionDTO(entity.getSurveyQuestion(), device, false));
//		setChild(new ChildDTO(entity.ge));
		setAgent(device.isNormal()? new UserDTO(entity.getAgent(), device, false) : null);
	}


	// TODO: getModel & Constructor
}
