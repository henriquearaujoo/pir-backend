package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Form;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = "questions", allowGetters = true)
public class FormDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 		String						id;

	@Getter
	@Setter
	@JsonProperty("from")
	@Min(value = 0, message = "invalid.from")
	private 		int							from;

	@Getter
	@Setter
	@JsonProperty("to")
	@Min(value = 0, message = "invalid.to")
	private 		int							to;

	@Getter
	@Setter
	@JsonProperty("age_zone")
	@Min(value = 0, message = "invalid.age.zone")
	private 		int							ageZone;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("in_years")
	private 		boolean						inYears;

	@Getter
	@Setter
	@JsonProperty("is_enabled")
	private 		boolean						enabled;

	@Getter
	@Setter
	@JsonProperty("version")
	private 		int							version;

	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	@JsonProperty(value = "questions", access = JsonProperty.Access.READ_ONLY)
	private 		Collection<FormQuestionDTO>	questions;

	public FormDTO() {
		super();
	}

	public FormDTO(Form form, boolean detailed) {
		setId(IDCoder.encode(form.getUuid()));
		setFrom(form.inYears()? form.getFromValue()/12 : form.getFromValue());
		setTo(form.inYears()? form.getToValue()/12 : form.getToValue());
		setAgeZone(form.getAgeZone());
		inYears(form.inYears());
		setEnabled(form.isEnabled());
		setVersion(form.getVersion());
		setQuestions(form.getQuestions() != null? form.getQuestions().stream().map(item -> new FormQuestionDTO(item, false)).collect(Collectors.toList()) : new ArrayList<>());
	}

	@JsonIgnore
	public Form getModel() {
		Form model = new Form();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setToValue(inYears()? getTo() * 12 : getTo());
		model.setFromValue(inYears()? getFrom() * 12 : getFrom());
		model.setAgeZone(getAgeZone());
		model.inYears(inYears());
		model.setEnabled(isEnabled());
		return model;
	}
}