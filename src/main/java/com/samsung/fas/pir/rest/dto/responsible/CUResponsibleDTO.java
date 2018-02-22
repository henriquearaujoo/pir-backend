package com.samsung.fas.pir.rest.dto.responsible;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.persistence.models.enums.EHabitationType;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// CREATE & UPDATE
@JsonIgnoreProperties(ignoreUnknown = true)
public class CUResponsibleDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String 			id;

	@Getter
	@Setter
	@JsonProperty("community_id")
	@NotBlank(message = "community.id.missing")
	private 	String			communityID;

	@Getter
	@Setter
	@JsonProperty("birth")
	@NotNull(message = "date.missing")
	@JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
	private 	String 			birth;

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

	@JsonIgnore
	public Responsible getModel() {
		Responsible model = new Responsible();

		model.setUuid(getId() != null? IDCoder.decode(getId()) : null);
		model.setInSocialProgram(isInSocialProgram());
		model.setHabitationType(EHabitationType.valueOf(getHabitationType()));
		model.setHabitationMembersCount(getHabitationMembersCount());
		model.setLiveWith(getLiveWith());
		model.setFamilyIncome(getFamilyIncome());
		model.setIncomeParticipation(getIncomeParticipation());
		model.setDrinkingWaterTreatment(getDrinkingWaterTreatment());
		model.setHasHospital(hasHospital());
		model.setHasSanitation(hasSanitation());
		model.setHasWaterTreatment(hasWaterTreatment());
		model.setObservations(getObservations());

		try {
			model.setBirth(new SimpleDateFormat("dd-MM-yyyy").parse(getBirth()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return model;
	}
}
