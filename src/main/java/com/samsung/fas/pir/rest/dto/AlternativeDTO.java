package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import com.samsung.fas.pir.persistence.models.Alternative;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@DTO(Alternative.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlternativeDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID 			uuid;

	@Getter
	@Setter
	@JsonProperty("question_id")
	private 	UUID			questionUUID;

	@Getter
	@Setter
	@JsonProperty("type")
	@NotNull(message = "type.missing")
	private 	EAnswerType 	type;

	@Getter
	@Setter
	@JsonProperty("answer")
	@NotBlank(message = "answer.missing")
	private 	String			answer;

	public AlternativeDTO() {
		super();
	}

	public AlternativeDTO(Alternative alternative, boolean detailed) {
		setUuid(alternative.getUuid());
		setAnswer(alternative.getDescription());
		setType(alternative.getType());
		setQuestionUUID(alternative.getQuestion().getUuid());
	}

	@JsonIgnore
	public Alternative getModel() {
		Alternative a	= new Alternative();
		a.setUuid(getUuid());
		a.setDescription(getAnswer());
		a.setType(getType());
		return a;
	}
}
