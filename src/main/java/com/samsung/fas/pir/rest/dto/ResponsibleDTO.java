package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.Responsible;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Responsible.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsibleDTO {
	// region Properties
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long		tempID;

	@Getter
	@Setter
	@JsonProperty("agent_id")
	private 	UUID 		agentUUID;

	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String		name;

	@Getter
	@Setter
	@JsonProperty("birth")
	@NotEmpty(message = "date.missing")
	@JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
	private 	String 		birth;

	@Getter
	@Setter
	@JsonProperty("in_social_program")
	private 	boolean			inSocialProgram;

	@Getter
	@Setter
	@JsonProperty("habitation_type")
	@NotNull(message = "habitation.type.missing")
	private 	EHabitationType	habitationType;

	@Getter
	@Setter
	@JsonProperty("habitation_members_count")
	@Min(value = 1, message = "invalid.counter")
	private 	int				habitationMembersCount;

	@Getter
	@Setter
	@JsonProperty("live_with")
	@NotBlank(message = "live.with.missing")
	private 	String			liveWith;

	@Getter
	@Setter
	@JsonProperty("family_income")
	@NotBlank(message = "family.income.missing")
	private 	String			familyIncome;

	@Getter
	@Setter
	@JsonProperty("income_participation")
	@NotBlank(message = "income.participation.missing")
	private 	String			incomeParticipation;

	@Getter
	@Setter
	@JsonProperty("drinking_water_treatment")
	@NotBlank(message = "drinking.water.missing")
	private 	String			drinkingWaterTreatment;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_hospital_nearby")
	private 	boolean			hasHospital;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_sanitation")
	private 	boolean			hasSanitation;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_water_treatment")
	private 	boolean			hasWaterTreatment;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_other_children")
	private 	boolean			hasOtherChildren;

	@Getter
	@Setter
	@JsonProperty("observations")
	private 	String			observations;

	@Getter
	@Setter
	@JsonProperty("children_count")
	@Min(value = 0, message = "invalid.count")
	private 	long			childrenCount;

	@Getter
	@Setter
	@JsonProperty("civil_state")
	@NotBlank(message = "civil.state.missing")
	private 	String			civilState;

	@Getter
	@Setter
	@JsonProperty("is_pregnant")
	private		boolean			pregnant;

	@Getter
	@Setter
	@JsonProperty("gender")
	private		String			gender;
	// endregion

	@Getter
	@Setter
	@JsonProperty("children")
	@Valid
	private 	List<ChildDTO>	children;

	@Getter
	@Setter
	@JsonProperty("pregnancies")
	private 	List<PregnancyDTO> 		pregnancies;

	@Getter
	@Setter
	@JsonProperty("community_id")
	private 	UUID			communityUUID;

	@Getter
	@Setter
	@JsonProperty("community")
	private 	CommunityDTO	community;

	public ResponsibleDTO() {
		super();
	}

	public ResponsibleDTO(Responsible responsible, Device device, boolean detailed) {
		setTempID(responsible.getMobileId());
		setName(responsible.getName());
		setUuid(responsible.getUuid());
		setBirth(new SimpleDateFormat("dd-MM-yyyy").format(responsible.getBirth()));
		setInSocialProgram(responsible.isInSocialProgram());
		setHabitationMembersCount(responsible.getHabitationMembersCount());
		setHabitationType(responsible.getHabitationType());
		setLiveWith(responsible.getLiveWith());
		setFamilyIncome(responsible.getFamilyIncome());
		setIncomeParticipation(responsible.getIncomeParticipation());
		setDrinkingWaterTreatment(responsible.getDrinkingWaterTreatment());
		hasHospital(responsible.isHasHospital());
		hasSanitation(responsible.isHasSanitation());
		hasWaterTreatment(responsible.isHasWaterTreatment());
		setObservations(responsible.getObservations());
		hasOtherChildren(responsible.isFamilyHasChildren());
		setChildrenCount(responsible.getChildrenCount());
		setCivilState(responsible.getCivilState().toString());
		setGender(responsible.getGender().getValue());
		setPregnant(responsible.isPregnant());
		setPregnancies(responsible.getPregnancies().stream().map(item -> new PregnancyDTO(item, device, false)).collect(Collectors.toList()));
		setChildren(responsible.getChildren().stream().map(item -> new ChildDTO(item, device, false)).collect(Collectors.toList()));
		setCommunity(device.isNormal()? new CommunityDTO(responsible.getCommunity(), device, false) : null);
	}

	@JsonIgnore
	public Responsible getModel() {
		Responsible model = new Responsible();
		model.setMobileId(getTempID());
		model.setFamilyHasChildren(hasOtherChildren());
		model.setName(getName());
		model.setUuid(getUuid());
		model.setInSocialProgram(isInSocialProgram());
		model.setHabitationType(getHabitationType());
		model.setHabitationMembersCount(getHabitationMembersCount());
		model.setLiveWith(getLiveWith());
		model.setFamilyIncome(getFamilyIncome());
		model.setIncomeParticipation(getIncomeParticipation());
		model.setDrinkingWaterTreatment(getDrinkingWaterTreatment());
		model.setHasHospital(hasHospital());
		model.setHasSanitation(hasSanitation());
		model.setHasWaterTreatment(hasWaterTreatment());
		model.setObservations(getObservations());
		model.setChildrenCount(getChildrenCount());
		model.setGender(EGender.valueOf(getGender()));
		model.setCivilState(ECivilState.valueOf(civilState));
		model.setPregnant(isPregnant());
		model.setCommunity(getCommunity() != null? getCommunity().getModel() : null);
		model.setChildren(getChildren() != null? getChildren().stream().map(ChildDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setPregnancies(pregnancies != null? pregnancies.stream().map(PregnancyDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setChildren(getChildren() != null? getChildren().stream().map(ChildDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());

		try {
			model.setBirth(new SimpleDateFormat("dd-MM-yyyy").parse(getBirth()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return model;
	}
}