package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SAlternative;
import com.samsung.fas.pir.persistence.models.SQuestion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@DTO(SAlternative.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SAlternativeDTO {
	@Getter
	@Setter
	@JsonProperty("description")
	private			String				description;

	@Getter
	@Setter
	@JsonProperty("value_type")
	private 		String				valueType;

	@Getter
	@Setter
	@JsonProperty("question")
	private 		SQuestionDTO		question;

	@Getter
	@Setter
	@JsonProperty("answers")
	private 		List<SAnswerDTO> 	answers;


	// TODO: getModel & Constructor
}
