package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	@JsonProperty("community_id")
	private 	UUID				communityUUID;

	@Getter
	@Setter
	@JsonProperty(value = "code", access = JsonProperty.Access.READ_ONLY)
	private 	String				code;

	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String				leaderName;

	@Getter
	@Setter
	@JsonProperty("birth")
	private 	Date 				birth;

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
	@JsonProperty("children_count")
	private 	long				childrenCount;

	@Getter
	@Setter
	@JsonProperty("drinking_water_treatment")
	private 	String				drinkableWaterTreatment;

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
	@JsonProperty("has_other_children")
	private 	boolean				otherChildren;

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
	@JsonProperty("children")
	@Valid
	private 	List<ChildDTO>		childrenDTO;

	@Getter
	@Setter
	@JsonProperty("pregnant")
	private 	List<PregnantDTO> 	pregnantDTO;

	@Getter
	@Setter
	@JsonProperty("community")
	private 	CommunityDTO		communityDTO;
	// endregion

	public FamilyDTO() {
		super();
	}

	public FamilyDTO(Family family, Device device, boolean detailed) {
		super(family);
//		setCommunityDTO();
		setPregnantDTO(device != null && !device.isNormal() || detailed? family.getPregnant().stream().map(item -> new PregnantDTO(item, device, false)).collect(Collectors.toList()) : null);
		setChildrenDTO(device != null && !device.isNormal() || detailed? family.getChildren().stream().map(item -> new ChildDTO(item, device, false)).collect(Collectors.toList()) : null);
		setCommunityDTO(device != null && device.isNormal()? new CommunityDTO(family.getCommunity(), device, false) : null);
	}

	@JsonIgnore
	@Override
	public Family getModel() {
		Family model = super.getModel();
		model.setCommunity(getCommunityDTO() != null? getCommunityDTO().getModel() : null);
		model.setChildren(getChildrenDTO() != null? getChildrenDTO().stream().map(ChildDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setPregnant(getPregnantDTO() != null? getPregnantDTO().stream().map(PregnantDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}