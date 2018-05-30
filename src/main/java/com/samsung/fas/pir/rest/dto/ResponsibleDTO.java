package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.Mother;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@DTO(Responsible.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsibleDTO {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long		tempID;

	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

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
	@NotNull(message = "date.missing")
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
	private 	boolean		hasHospital;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_sanitation")
	private 	boolean		hasSanitation;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_water_treatment")
	private 	boolean		hasWaterTreatment;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_other_children")
	private 	boolean		hasOtherChildren;

	@Getter
	@Setter
	@JsonProperty("observations")
	private 	String		observations;

	@Getter
	@Setter
	@JsonProperty("mother")
	@Valid
	private 	MotherDTO		mother;

	@Getter
	@Setter
	@JsonProperty("community")
	@NotNull(message = "community.missing")
	@Valid
	private 	CommunityDTO	community;

	public ResponsibleDTO() {
		super();
	}

	public ResponsibleDTO(Mother mother, boolean detailed) {
		this(mother.getResponsible(), true);
		setMother(new MotherDTO(mother, false));
	}

	public ResponsibleDTO(Responsible responsible, boolean detailed) {
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
		setCommunity(new CommunityDTO(responsible.getCommunity(), false));
		setMother(responsible.getMother() != null? new MotherDTO(responsible.getMother(), false) : null);
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
		model.setMother(getMother() != null? getMother().getModel() : null);
		model.setCommunity(getCommunity() != null? getCommunity().getModel() : null);

		if (model.getMother() != null) {
			model.getMother().setResponsible(model);
		}

		try {
			model.setBirth(new SimpleDateFormat("dd-MM-yyyy").parse(getBirth()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return model;
	}
}