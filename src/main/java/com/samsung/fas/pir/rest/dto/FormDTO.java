package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.persistence.models.FormAnswerTB;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.mobile.device.Device;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@DTO(FormAnswerTB.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = "questions", allowGetters = true)
public class FormDTO extends BaseDTO<Form> {
	@Getter
	@Setter
	@JsonProperty("version")
	private 		int			version;

	@Getter
	@Setter
	@JsonProperty("age_zone")
	@Min(value = 0, message = "invalid.age.zone")
	private 		int			ageZone;

	@Getter
	@Setter
	@JsonProperty("from")
	@Min(value = 0, message = "invalid.from")
	private 		int			fromValue;

	@Getter
	@Setter
	@JsonProperty("to")
	@Min(value = 0, message = "invalid.to")
	private 		int			toValue;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("in_years")
	private 		boolean		inYears;

	@Getter
	@Setter
	@JsonProperty("is_enabled")
	private 		boolean		enabled;

	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	@JsonProperty(value = "questions", access = JsonProperty.Access.READ_ONLY)
	private 		Collection<FormQuestionDTO>		questionsDTO;

	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	@JsonProperty(value = "visits", access = JsonProperty.Access.READ_ONLY)
	private 		Collection<VisitDTO>			visitsDTO;

	public FormDTO() {
		super();
	}

	public FormDTO(Form form, Device device, boolean detailed) {
		super(form);
		setFromValue(form.inYears()? form.getFromValue()/12 : form.getFromValue());
		setToValue(form.inYears()? form.getToValue()/12 : form.getToValue());
		setQuestionsDTO(form.getQuestions() != null? form.getQuestions().stream().map(item -> new FormQuestionDTO(item, device, false)).collect(Collectors.toList()) : new ArrayList<>());
	}

	@JsonIgnore
	@Override
	public Form getModel() {
		Form model = super.getModel();
		model.setToValue(inYears()? getToValue() * 12 : getToValue());
		model.setFromValue(inYears()? getFromValue() * 12 : getFromValue());
		return model;
	}
}