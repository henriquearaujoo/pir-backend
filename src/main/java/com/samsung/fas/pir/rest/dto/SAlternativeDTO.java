package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.SAlternative;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

@DTO(SAlternative.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SAlternativeDTO extends BaseDTO<SAlternative> {
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
	private 		SQuestionDTO		questionDTO;

	public SAlternativeDTO() {
		super();
	}

	public SAlternativeDTO(SAlternative entity, Device device, boolean detailed) {
		super(entity);
		setQuestionDTO(detailed? new SQuestionDTO(entity.getQuestion(), device, false) : null);
	}

	@JsonIgnore
	@Override
	public SAlternative getModel() {
		SAlternative model = super.getModel();
		model.setQuestion(getQuestionDTO() != null? getQuestionDTO().getModel() : null);
		return model;
	}
}
