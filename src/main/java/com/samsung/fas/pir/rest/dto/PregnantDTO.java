package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.models.Pregnant;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Pregnant.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PregnantDTO extends BaseDTO<Pregnant> {
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
	private		Date 						birth;

	@Getter
	@Setter
	@JsonProperty("civil_state")
	private 	ECivilState 				civilState;

	@Getter
	@Setter
	@JsonProperty("phone_number")
	private 	String						phoneNumber;

	@Getter
	@Setter
	@JsonProperty("phone_owner")
	private 	boolean						phoneResponsible;

	// region DTO Relations
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
	@JsonProperty("family")
	private 	FamilyDTO					familyDTO;

	@Getter
	@Setter
	@JsonProperty("pregnancies")
	private 	List<PregnancyDTO> 			pregnanciesDTO;

	@Getter
	@Setter
	@JsonProperty("agent")
	private 	AgentDTO						responsibleAgentDTO;
	// endregion

	public PregnantDTO() {
		super();
	}

	public PregnantDTO(Pregnant pregnant, Device device, boolean detailed) {
		super(pregnant);
		if (!device.isNormal()) {
			setFamilyUUID(pregnant.getFamily() != null? pregnant.getFamily().getUuid() : null);
			setAgentUUID(pregnant.getAgent() != null? pregnant.getAgent().getUuid() : null);
			setPregnanciesDTO(pregnant.getPregnancies().stream().map(item -> new PregnancyDTO(item, device, false)).collect(Collectors.toList()));
		} else {
			setFamilyDTO(detailed? pregnant.getFamily() != null? new FamilyDTO(pregnant.getFamily(), device, false) : null : null);
			setResponsibleAgentDTO(pregnant.getAgent() != null? new AgentDTO(pregnant.getAgent(), device, false) : null);
			setPregnanciesDTO(pregnant.getPregnancies().stream().map(item -> new PregnancyDTO(item, device, false)).collect(Collectors.toList()));
		}
	}

	@JsonIgnore
	@Override
	public Pregnant getModel() {
		Pregnant model = super.getModel();
		model.setPregnancies(getPregnanciesDTO() != null? getPregnanciesDTO().stream().map(PregnancyDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}