package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SQuestion;
import com.samsung.fas.pir.persistence.models.Survey;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@DTO(SQuestion.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SQuestionDTO {
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
	private 		List<SAlternativeDTO>	alternative;

	// TODO: getModel & Constructor
}
