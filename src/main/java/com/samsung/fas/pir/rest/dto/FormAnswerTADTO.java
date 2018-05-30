package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.FormAnswerTA;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

@DTO(FormAnswerTA.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormAnswerTADTO {
	@Getter
	@Setter
	@JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
	private 	UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("form_id")
	@NotBlank(message = "form.id.missing")
	private 	UUID		formUUID;

	@Getter
	@Setter
	@JsonProperty(value = "question_id", access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "question.id.missing")
	private 	String		questionUUID;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("can_do_alone")
	private 	boolean		canDoAlone;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("can_do_with_help")
	private 	boolean		canDoWithHelp;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("can_not_do")
	private 	boolean		canNotDo;

	@Getter
	@Setter
	@JsonProperty(value = "question", access = JsonProperty.Access.READ_ONLY)
	private 	FormQuestionDTO	question;

	@Getter
	@Setter
	@JsonProperty(value = "child", access = JsonProperty.Access.READ_ONLY)
	private 	ChildDTO 	child;

	public FormAnswerTADTO() {
		super();
	}

	public FormAnswerTADTO(FormAnswerTA answer, boolean detailed) {
		setUuid(answer.getUuid());
		canDoAlone(answer.canDoAlone());
		canDoWithHelp(answer.canDoWithHelp());
		canNotDo(answer.canNotDo());
		setQuestion(new FormQuestionDTO(answer.getQuestion(), false));
		setChild(new ChildDTO(answer.getChild(), false));
	}
}
