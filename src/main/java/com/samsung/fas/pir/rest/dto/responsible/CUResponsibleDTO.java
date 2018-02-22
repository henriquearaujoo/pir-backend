package com.samsung.fas.pir.rest.dto.responsible;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CUResponsibleDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String 			id;

	@Getter
	@Setter
	@JsonProperty("community_id")
	private 	String			communityID;

	@Getter
	@Setter
	@JsonProperty("birth")
	@NotNull(message = "date.missing")
	private 	Date 			birth;

	@Getter
	@Setter
	@JsonProperty("in_social_program")
	private 	boolean			inSocialProgram;

	@Getter
	@Setter
	@JsonProperty("habitation_type")
	@NotBlank(message = "habitation.type.missing")
	private 	String			habitationType;

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

	@Getter
	@Setter
	@JsonProperty("has_hospital_nearby")
	private 	boolean			hasHospital;

	@Getter
	@Setter
	@JsonProperty("has_sanitation")
	private 	boolean			hasSanitation;

	@Getter
	@Setter
	@JsonProperty("has_water_treatment")
	private 	boolean			hasWaterTreatment;

	@Getter
	@Setter
	@JsonProperty("observations")
	private 	String			observations;
}
