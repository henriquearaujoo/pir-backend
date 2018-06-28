package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SAlternative;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.UUID;

@DTO(SAlternative.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SAlternativeDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 		UUID				id;

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

	public SAlternativeDTO() {
		super();
	}

	public SAlternativeDTO(SAlternative entity, Device device, boolean detailed) {
		setId(entity.getUuid());
		setDescription(entity.getDescription());
		setValueType(entity.getValueType());
		setQuestion(detailed? new SQuestionDTO(entity.getQuestion(), device, false) : null);
	}

	@JsonIgnore
	public SAlternative getModel() {
		SAlternative model = new SAlternative();
		model.setUuid(getId());
		model.setValueType(getValueType());
		model.setDescription(getDescription());
		model.setQuestion(getQuestion() != null? getQuestion().getModel() : null);
		return model;
	}
}
