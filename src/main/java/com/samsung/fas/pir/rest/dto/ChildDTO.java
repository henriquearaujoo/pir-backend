package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Child.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildDTO extends BaseDTO<Child> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long						externalID;

	@Getter
	@Setter
	@JsonProperty(value = "code", access = JsonProperty.Access.READ_ONLY)
	private 	String						code;

	@Getter
	@Setter
	@JsonProperty("name")
	private 	String						name;

	@Getter
	@Setter
	@JsonProperty("birth")
	private 	Date						birth;

	@Getter
	@Setter
	@JsonProperty("gender")
	private		EGender						gender;

	@Getter
	@Setter
	@JsonProperty("mother_name")
	private 	String						motherFullName;

	@Getter
	@Setter
	@JsonProperty("father_name")
	private 	String						fatherFullName;

	// region Relations
	@Getter
	@Setter
	@JsonProperty("agent_id")
	private		UUID						agentUUID;

	@Getter
	@Setter
	@JsonProperty("family_id")
	private		UUID						familyUUID;

	@Getter
	@Setter
	@JsonProperty("agent")
	@Valid
	private 	UserDTO 					agent;

	@Getter
	@Setter
	@JsonProperty("answers")
	@Valid
	private		List<SAnswerDTO>			answersDTO;

	@Getter
	@Setter
	@JsonProperty("visits")
	@Valid
	private		List<VisitDTO>				visitsDTO;

	@Getter
	@Setter
	@JsonProperty("family")
	private 	FamilyDTO					familyDTO;
	// endregion

	public ChildDTO() {
		super();
	}

	public ChildDTO(Child child, Device device, boolean detailed) {
		super(child);
		if (!device.isNormal()) {
			setAgentUUID(child.getAgent() != null? child.getAgent().getUuid() : null);
			setFamilyUUID(child.getFamily() != null? child.getFamily().getUuid() : null);
			setAnswersDTO(child.getAnswers().stream().map(item -> new SAnswerDTO(item, device, false)).collect(Collectors.toList()));
			setVisitsDTO(child.getVisits().stream().map(item -> new VisitDTO(item, device, false)).collect(Collectors.toList()));
		} else {
			setFamilyDTO(detailed? new FamilyDTO(child.getFamily(), device, false) : null);
			setAgent(child.getAgent() != null? new UserDTO(child.getAgent(), device, false) : null);
			setAnswersDTO(child.getAnswers().stream().map(item -> new SAnswerDTO(item, device, false)).collect(Collectors.toList()));
			setVisitsDTO(child.getVisits().stream().map(item -> new VisitDTO(item, device, false)).collect(Collectors.toList()));
		}
	}

	@JsonIgnore
	@Override
	public Child getModel() {
		Child model = new Child();

		model.setUuid(getUuid());
		model.setExternalID(getExternalID());
		model.setCode(getCode());
		model.setName(getName());
		model.setBirth(getBirth());
		model.setGender(getGender());
		model.setMotherFullName(getMotherFullName());
		model.setFatherFullName(getFatherFullName());

		model.setAnswers(getAnswersDTO() != null? getAnswersDTO().stream().map(SAnswerDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setVisits(getVisitsDTO() != null? getVisitsDTO().stream().map(VisitDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}

}
