package com.samsung.fas.pir.rest.dto.responsible;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.rest.dto.IReadDTO;
import com.samsung.fas.pir.rest.dto.community.RCommunityDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RResponsibleDTO implements IReadDTO<Responsible, RResponsibleDTO> {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String 			id;

	@Getter
	@Setter
	@JsonProperty("community_id")
	private 	RCommunityDTO	communityID;

	@Getter
	@Setter
	@JsonProperty("birth")
	@JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
	private 	Date 			birth;

	@Getter
	@Setter
	@JsonProperty("in_social_program")
	private 	boolean			inSocialProgram;

	@Getter
	@Setter
	@JsonProperty("habitation_type")
	private 	String			habitationType;

	@Getter
	@Setter
	@JsonProperty("habitation_members_count")
	private 	int				habitationMembersCount;

	@Getter
	@Setter
	@JsonProperty("live_with")
	private 	String			liveWith;

	@Getter
	@Setter
	@JsonProperty("family_income")
	private 	String			familyIncome;

	@Getter
	@Setter
	@JsonProperty("income_participation")
	private 	String			incomeParticipation;

	@Getter
	@Setter
	@JsonProperty("drinking_water_treatment")
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

	@Getter
	@Setter
	@JsonProperty("observations")
	private 	String			observations;

	public RResponsibleDTO() {
		super();
	}

	private RResponsibleDTO(Responsible responsible) {
		setId(IDCoder.encode(responsible.getUuid()));
		setCommunityID(new RCommunityDTO().toDTO(responsible.getCommunity()));
		setBirth(responsible.getBirth());
		setInSocialProgram(responsible.isInSocialProgram());
		setHabitationMembersCount(responsible.getHabitationMembersCount());
		setHabitationType(responsible.getHabitationType().toString());
		setLiveWith(responsible.getLiveWith());
		setFamilyIncome(responsible.getFamilyIncome());
		setIncomeParticipation(responsible.getIncomeParticipation());
		setDrinkingWaterTreatment(responsible.getDrinkingWaterTreatment());
		hasHospital(responsible.isHasHospital());
		hasSanitation(responsible.isHasSanitation());
		hasWaterTreatment(responsible.isHasWaterTreatment());
		setObservations(responsible.getObservations());
	}

	@Override
	public RResponsibleDTO toDTO(Responsible responsible) {
		return responsible != null? new RResponsibleDTO(responsible) : null;
	}
}
