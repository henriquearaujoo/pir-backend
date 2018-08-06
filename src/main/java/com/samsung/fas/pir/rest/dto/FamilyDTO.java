package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Family.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FamilyDTO extends BaseDTO<Family> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long				externalID;

	@Getter
	@Setter
	@JsonProperty(value = "code", access = JsonProperty.Access.READ_ONLY)
	private 	String				code;

	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String				name;

	@Getter
	@Setter
	@JsonProperty("habitation_type")
	private 	EHabitationType		habitationType;

	@Getter
	@Setter
	@JsonProperty("habitation_members_count")
	private 	int					membersCount;

	@Getter
	@Setter
	@JsonProperty("family_income")
	private 	String				familyIncome;

	@Getter
	@Setter
	@JsonProperty("family_income_other")
	private 	String				familyIncomeOther;

	@Getter
	@Setter
	@JsonProperty("water_treatment_description")
	private 	String				waterTreatmentDescription;

	@Getter
	@Setter
	@JsonProperty("has_hospital_nearby")
	private 	boolean				nearbyUB;

	@Getter
	@Setter
	@JsonProperty("has_sanitation")
	private 	boolean				sanitation;

	@Getter
	@Setter
	@JsonProperty("has_water_treatment")
	private 	boolean				waterTreatment;

	@Getter
	@Setter
	@JsonProperty("observations")
	private 	String				observations;

	// region DTO Relations
	@Getter
	@Setter
	@JsonProperty("community_id")
	private 	UUID				communityUUID;

	@Getter
	@Setter
	@JsonProperty("agent_id")
	private 	UUID				agentUUID;

	@Getter
	@Setter
	@JsonProperty("children")
	@Valid
	private 	List<ChildDTO>		childrenDTO;

	@Getter
	@Setter
	@JsonProperty("pregnant")
	private 	List<PregnantDTO> 	pregnantDTO;

	@ApiModelProperty(readOnly = true)
	@Getter
	@Setter
	@JsonProperty(value = "agent", access = JsonProperty.Access.READ_ONLY)
	private 	UserDTO				agentDTO;

	@ApiModelProperty(readOnly = true)
	@Getter
	@Setter
	@JsonProperty(value = "community", access = JsonProperty.Access.READ_ONLY)
	private 	CommunityDTO		communityDTO;
	// endregion

	public FamilyDTO() {
		super();
	}

	public FamilyDTO(Family family, Device device, boolean detailed) {
		super(family);
		if (!device.isNormal()) {
			setCommunityUUID(family.getCommunity().getUuid());
			setPregnantDTO(family.getPregnant().stream().map(item -> new PregnantDTO(item, device, false)).collect(Collectors.toList()));
			setChildrenDTO(family.getChildren().stream().map(item -> new ChildDTO(item, device, false)).collect(Collectors.toList()));
		} else {
			setCommunityDTO(new CommunityDTO(family.getCommunity(), device, false));
			setAgentDTO(new UserDTO(family.getAgent().getPerson().getUser(), device, false));
			setPregnantDTO(detailed? family.getPregnant().stream().map(item -> new PregnantDTO(item, device, false)).collect(Collectors.toList()) : null);
			setChildrenDTO(detailed? family.getChildren().stream().map(item -> new ChildDTO(item, device, false)).collect(Collectors.toList()) : null);
		}
	}

	@JsonIgnore
	@Override
	public Family getModel() {
		Family model = new Family();

		model.setUuid(getUuid());
		model.setExternalID(getExternalID());
		model.setCode(getCode());
		model.setName(getName());
		model.setHabitationType(getHabitationType());
		model.setMembersCount(getMembersCount());
		model.setFamilyIncome(getFamilyIncome());
		model.setFamilyIncomeOther(getFamilyIncomeOther());
		model.setWaterTreatmentDescription(getWaterTreatmentDescription());
		model.setWaterTreatment(isWaterTreatment());
		model.setObservations(getObservations());

		model.setCommunity(getCommunityDTO() != null? getCommunityDTO().getModel() : null);
		model.setChildren(getChildrenDTO() != null? getChildrenDTO().stream().map(ChildDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setPregnant(getPregnantDTO() != null? getPregnantDTO().stream().map(PregnantDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}