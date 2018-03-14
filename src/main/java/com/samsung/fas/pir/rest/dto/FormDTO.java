package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Form;
import com.samsung.fas.pir.persistence.models.enums.EFormType;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 		String							id;

	@Getter
	@Setter
	@JsonProperty("from")
	@Min(value = 0, message = "invalid.from")
	private 		int								from;

	@Getter
	@Setter
	@JsonProperty("to")
	@Min(value = 0, message = "invalid.to")
	private 		int								to;

	@Getter
	@Setter
	@JsonProperty("range")
	@Min(value = 0, message = "invalid.range")
	private 		int								range;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("in_years")
	private 		boolean							inYears;

	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	@JsonProperty(value = "type", access = JsonProperty.Access.READ_ONLY)
	private 		EFormType						type;

	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	@JsonProperty(value = "questions_type_a", access = JsonProperty.Access.READ_ONLY)
	private 		Collection<FormQuestionTADTO>	questionsTA;

	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	@JsonProperty(value = "questions_type_b", access = JsonProperty.Access.READ_ONLY)
	private 		Collection<FormQuestionTBDTO>	questionsTB;

	public FormDTO() {
		super();
	}

	public FormDTO(Form form, boolean detailed) {
		setId(IDCoder.encode(form.getUuid()));
		setFrom(getFrom());
		setTo(getTo());
		setRange(form.getRange());
		inYears(form.inYears());
		setType(form.getQuestionsTA() != null && form.getQuestionsTA().size() > 0? EFormType.ATYPE : form.getQuestionsTB() != null && form.getQuestionsTB().size() > 0? EFormType.BTYPE : EFormType.UNDEFINED);
		setQuestionsTA(form.getQuestionsTA() != null? form.getQuestionsTA().stream().map(item -> new FormQuestionTADTO(item, false)).collect(Collectors.toSet()) : null);
		setQuestionsTB(form.getQuestionsTB() != null? form.getQuestionsTB().stream().map(item -> new FormQuestionTBDTO(item, false)).collect(Collectors.toSet()) : null);
	}

	@JsonIgnore
	public Form getModel() {
		Form model = new Form();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setToValue(getTo());
		model.setFromValue(getFrom());
		model.setRange(getRange());
		model.inYears(inYears());
		return model;
	}
}