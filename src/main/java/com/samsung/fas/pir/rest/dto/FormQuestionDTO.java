package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EFormQuestionType;
import com.samsung.fas.pir.persistence.models.FormQuestion;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@DTO(FormQuestion.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormQuestionDTO extends BaseDTO<FormQuestion> {
	@Getter
	@Setter
	@JsonProperty("form_id")
	private 	UUID		formUUID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String		description;

	@Getter
	@Setter
	@JsonProperty("type")
	@NotNull(message = "type.missing")
	private 	String		type;

	@Getter
	@Setter
	@JsonProperty("is_enabled")
	private 	boolean		enabled;

	@Setter(value = AccessLevel.PRIVATE)
	@Getter
	@JsonProperty(value = "form", access = JsonProperty.Access.READ_ONLY)
	private 	FormDTO		formDTO;

	public FormQuestionDTO() {
		super();
	}

	public FormQuestionDTO(FormQuestion question, Device device, boolean detailed) {
		super(question);
		setFormDTO(detailed? new FormDTO(question.getForm(), device, false) : null);
	}

	@JsonIgnore
	@Override
	public FormQuestion getModel() {
		FormQuestion model = super.getModel();
		model.setType(getType() == null? EFormQuestionType.UNDEFINED : EFormQuestionType.setValue(getType()));
		return model;
	}
}
