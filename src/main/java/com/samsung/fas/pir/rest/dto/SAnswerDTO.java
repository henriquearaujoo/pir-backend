package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.List;
import java.util.UUID;

@DTO(SAnswer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SAnswerDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID				id;

	@Getter
	@Setter
	@JsonProperty("description")
	private 	String				description;

	@Getter
	@Setter
	@JsonProperty("alternative_id")
	private 	UUID				alternativeUUID;

	@Getter
	@Setter
	@JsonProperty("question_id")
	private 	UUID				questionUUID;

	@Getter
	@Setter
	@JsonProperty("child_id")
	private 	UUID				childUUID;

	@Getter
	@Setter
	@JsonProperty("responsible_id")
	private 	UUID				responsibleUUID;

	// region READ ONLY
	@Getter
	@Setter
	@JsonProperty(value = "alternative", access = JsonProperty.Access.READ_ONLY)
	private		SAlternativeDTO		alternative;

	@Getter
	@Setter
	@JsonProperty(value = "question", access = JsonProperty.Access.READ_ONLY)
	private		SQuestionDTO		question;

	@Getter
	@Setter
	@JsonProperty(value = "agent", access = JsonProperty.Access.READ_ONLY)
	private 	UserDTO				agent;

	@Getter
	@Setter
	@JsonProperty(value = "child", access = JsonProperty.Access.READ_ONLY)
	private 	ChildDTO 			child;

	@Getter
	@Setter
	@JsonProperty(value = "responsible", access = JsonProperty.Access.READ_ONLY)
	private 	ResponsibleDTO 		responsible;
	// endregion

	public SAnswerDTO() {
		super();
	}

	public SAnswerDTO(SAnswer entity, Device device, boolean detailed) {
		setId(entity.getUuid());
		setDescription(entity.getDescription());
		setAlternative(entity.getAlternative() != null? new SAlternativeDTO(entity.getAlternative(), device, false) : null);
		setQuestion(new SQuestionDTO(entity.getQuestion(), device, false));
		setChild(entity.getChild() != null? new ChildDTO(entity.getChild(), device, false) : null);
		setResponsible(entity.getResponsible() != null? new ResponsibleDTO(entity.getResponsible(), device, false) : null);
		setAgent(device.isNormal()? new UserDTO(entity.getAgent(), device, false) : null);
	}

	@JsonIgnore
	public SAnswer getModel() {
		SAnswer 		model 			= new SAnswer();
		Child			child			= new Child();
		Responsible		responsible		= new Responsible();
		SQuestion		question		= new SQuestion();
		SAlternative	alternative		= new SAlternative();

		question.setUuid(getQuestionUUID());
		child.setUuid(getChildUUID());
		responsible.setUuid(getResponsibleUUID());
		alternative.setUuid(getAlternativeUUID());
		model.setUuid(getId());
		model.setDescription(getDescription());
		model.setQuestion(getQuestion() != null? getQuestion().getModel() : null);
		model.setAlternative(getAlternative() != null? getAlternative().getModel() : null);
		model.setResponsible(responsible);
		model.setChild(child);
		model.setQuestion(question);
		model.setAlternative(alternative);
		return model;
	}
}
