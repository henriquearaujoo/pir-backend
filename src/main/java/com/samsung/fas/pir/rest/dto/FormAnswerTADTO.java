package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.FormAnswerTA;
import com.samsung.fas.pir.utils.IDCoder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormAnswerTADTO {
	@ApiModelProperty(allowEmptyValue = true)
	@Getter
	@Setter
	@JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
	private		String				id;

	@ApiModelProperty
	@Getter
	@Setter
	@JsonProperty("form_id")
	@NotBlank(message = "form.id.missing")
	private 	String				formID;

	@ApiModelProperty
	@Getter
	@Setter
	@JsonProperty(value = "question_id", access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "question.id.missing")
	private 	String				questionID;

	@ApiModelProperty
	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("can_do_alone")
	private 	boolean				canDoAlone;

	@ApiModelProperty
	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("can_do_with_help")
	private 	boolean				canDoWithHelp;

	@ApiModelProperty
	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("can_not_do")
	private 	boolean				canNotDo;

	@ApiModelProperty(readOnly = true)
	@Getter
	@Setter
	@JsonProperty(value = "question", access = JsonProperty.Access.READ_ONLY)
	private 	FormQuestionDTO		question;

	@ApiModelProperty(readOnly = true)
	@Getter
	@Setter
	@JsonProperty(value = "child", access = JsonProperty.Access.READ_ONLY)
	private 	ChildDTO			child;

	public FormAnswerTADTO() {
		super();
	}

	public FormAnswerTADTO(FormAnswerTA answer, boolean detailed) {
		setId(IDCoder.encode(answer.getUuid()));
		canDoAlone(answer.canDoAlone());
		canDoWithHelp(answer.canDoWithHelp());
		canNotDo(answer.canNotDo());
		setQuestion(new FormQuestionDTO(answer.getQuestion(), false));
		setChild(new ChildDTO(answer.getChild(), false));
	}
}
