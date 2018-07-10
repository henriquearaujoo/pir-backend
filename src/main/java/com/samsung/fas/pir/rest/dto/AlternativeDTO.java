package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import com.samsung.fas.pir.persistence.models.Alternative;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.UUID;

@DTO(Alternative.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlternativeDTO extends BaseDTO<Alternative> {
	@Getter
	@Setter
	@JsonProperty("question_id")
	private 	UUID			questionUUID;

	@Getter
	@Setter
	@JsonProperty("answer")
	private 	String			description;

	@Getter
	@Setter
	@JsonProperty("type")
	private 	EAnswerType 	type;

	public AlternativeDTO() {
		super();
	}

	public AlternativeDTO(Alternative alternative, Device device, boolean detailed) {
		super(alternative);
		setType(type == null? EAnswerType.UNDEFINED : type);
		setQuestionUUID(alternative.getQuestion().getUuid());
	}
}
