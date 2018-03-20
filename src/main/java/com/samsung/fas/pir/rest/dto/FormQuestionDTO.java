package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.FormQuestion;
import com.samsung.fas.pir.persistence.models.enums.EFormQuestionType;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormQuestionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 		String					id;

	@Getter
	@Setter
	@JsonProperty("form_id")
	private 		String					formID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 		String					description;

	@Getter
	@Setter
	@JsonProperty("type")
	@NotNull(message = "type.missing")
	private 		String					type;

	@Getter
	@Setter
	@JsonProperty("is_enabled")
	private 		boolean					enabled;

	@Setter(value = AccessLevel.PRIVATE)
	@Getter
	@JsonProperty(value = "form", access = JsonProperty.Access.READ_ONLY)
	private 		FormDTO					form;

	public FormQuestionDTO() {
		super();
	}

	public FormQuestionDTO(FormQuestion question, boolean detailed) {
		setId(IDCoder.encode(question.getUuid()));
		setDescription(question.getDescription());
		setType(question.getType().getValue());
		setEnabled(question.isEnabled());
		setForm(detailed? new FormDTO(question.getForm(), false) : null);
	}

	@JsonIgnore
	public FormQuestion getModel() {
		FormQuestion model = new FormQuestion();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setDescription(getDescription());
		model.setType(EFormQuestionType.setValue(getType()));
		model.setEnabled(isEnabled());
		return model;
	}
}
