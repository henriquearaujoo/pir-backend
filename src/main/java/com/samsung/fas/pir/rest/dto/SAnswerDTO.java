package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.UUID;

@DTO(SAnswer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SAnswerDTO extends BaseDTO<SAnswer> {
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
	@JsonProperty("pregnancy_id")
	private 	UUID				pregnancyUUID;

	// region READ ONLY
	@Getter
	@Setter
	@JsonProperty(value = "alternative", access = JsonProperty.Access.READ_ONLY)
	private		SAlternativeDTO		alternativeDTO;

	@Getter
	@Setter
	@JsonProperty(value = "question", access = JsonProperty.Access.READ_ONLY)
	private		SQuestionDTO		questionDTO;

	@Getter
	@Setter
	@JsonProperty(value = "agent", access = JsonProperty.Access.READ_ONLY)
	private 	UserDTO				agentDTO;

	@Getter
	@Setter
	@JsonProperty(value = "child", access = JsonProperty.Access.READ_ONLY)
	private 	ChildDTO 			childDTO;

	@Getter
	@Setter
	@JsonProperty(value = "family", access = JsonProperty.Access.READ_ONLY)
	private 	PregnancyDTO 		pregnancyDTO;
	// endregion

	public SAnswerDTO() {
		super();
	}

	public SAnswerDTO(SAnswer entity, Device device, boolean detailed) {
		super(entity);
		setAlternativeDTO(entity.getAlternative() != null? new SAlternativeDTO(entity.getAlternative(), device, false) : null);
		setQuestionDTO(new SQuestionDTO(entity.getQuestion(), device, false));
		setChildDTO(detailed? entity.getChild() != null? new ChildDTO(entity.getChild(), device, false) : null : null);
		setPregnancyDTO(detailed? entity.getPregnancy() != null? new PregnancyDTO(entity.getPregnancy(), device, false) : null : null);
		setAgentDTO(detailed? device.isNormal()? new UserDTO(entity.getAgent(), device, false) : null : null);
	}

	@JsonIgnore
	@Override
	public SAnswer getModel() {
		SAnswer 		model 			= super.getModel();
		Child			child			= new Child();
		Pregnancy		pregnancy		= new Pregnancy();
		SQuestion		question		= new SQuestion();
		SAlternative	alternative		= new SAlternative();

		question.setUuid(getQuestionUUID());
		child.setUuid(getChildUUID());
		pregnancy.setUuid(getPregnancyUUID());
		alternative.setUuid(getAlternativeUUID());
		model.setQuestion(getQuestionDTO() != null? getQuestionDTO().getModel() : null);
		model.setAlternative(getAlternativeDTO() != null? getAlternativeDTO().getModel() : null);
		model.setPregnancy(pregnancy);
		model.setChild(child);
		model.setQuestion(question);
		model.setAlternative(alternative);
		return model;
	}
}