package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.QuestionTB;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormQuestionTBDTO {
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

	@Accessors(fluent = true)
	@Setter(value = AccessLevel.PRIVATE)
	@Getter
	@JsonProperty(value = "is_present", access = JsonProperty.Access.READ_ONLY)
	private 		Boolean					isPresent;

	@Setter(value = AccessLevel.PRIVATE)
	@Getter
	@JsonProperty(value = "form", access = JsonProperty.Access.READ_ONLY)
	private 		FormDTO					form;

	public FormQuestionTBDTO() {
		super();
	}

	public FormQuestionTBDTO(QuestionTB question, boolean detailed) {
		setId(IDCoder.encode(question.getUuid()));
		setDescription(question.getDescription());
		isPresent(question.isPresent());
		setForm(new FormDTO(question.getForm(), false));
	}

	@JsonIgnore
	public QuestionTB getModel() {
		QuestionTB model = new QuestionTB();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setDescription(getDescription());
		return model;
	}
}
