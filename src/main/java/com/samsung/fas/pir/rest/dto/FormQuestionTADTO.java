package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.QuestionTA;
import com.samsung.fas.pir.persistence.models.enums.EDimensionQuestionTA;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormQuestionTADTO {
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
	@JsonProperty(value = "can_do_alone", access = JsonProperty.Access.READ_ONLY)
	private 		Boolean					canDoAlone;

	@Accessors(fluent = true)
	@Setter(value = AccessLevel.PRIVATE)
	@Getter
	@JsonProperty(value = "can_do_with_help", access = JsonProperty.Access.READ_ONLY)
	private 		Boolean					canDoWithHelp;

	@Accessors(fluent = true)
	@Setter(value = AccessLevel.PRIVATE)
	@Getter
	@JsonProperty(value = "cannot_do", access = JsonProperty.Access.READ_ONLY)
	private 		Boolean					canNotDo;

	@Getter
	@Setter
	@JsonProperty("dimension")
	@NotNull(message = "dimension.missing")
	private 		EDimensionQuestionTA	dimension;

	@Setter(value = AccessLevel.PRIVATE)
	@Getter
	@JsonProperty(value = "form", access = JsonProperty.Access.READ_ONLY)
	private 		FormDTO					form;

	public FormQuestionTADTO() {
		super();
	}

	public FormQuestionTADTO(QuestionTA question, boolean detailed) {
		setId(IDCoder.encode(question.getUuid()));
		setDescription(question.getDescription());
		canDoAlone(question.canDoAlone());
		canDoWithHelp(question.canDoWithHelp());
		canNotDo(question.canNotDo());
		setDimension(question.getDimension());
		setForm(new FormDTO(question.getForm(), false));
	}

	@JsonIgnore
	public QuestionTA getModel() {
		QuestionTA model = new QuestionTA();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setDescription(getDescription());
		model.setDimension(getDimension());
		return model;
	}
}
