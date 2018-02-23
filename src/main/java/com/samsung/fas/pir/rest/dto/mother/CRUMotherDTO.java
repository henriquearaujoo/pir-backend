package com.samsung.fas.pir.rest.dto.mother;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.persistence.models.enums.ECivilState;
import com.samsung.fas.pir.persistence.models.enums.EHabitationType;
import com.samsung.fas.pir.rest.dto.responsible.CRUResponsibleDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CRUMotherDTO extends CRUResponsibleDTO {
	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String		name;

	@Getter
	@Setter
	@JsonProperty("children_count")
	@Min(value = 1, message = "invalid.count")
	private 	long		children;

	@Getter
	@Setter
	@JsonProperty("civil_state")
	@NotBlank(message = "civil.state.missing")
	private 	String		civilState;

	public CRUMotherDTO() {
		super();
	}

	public CRUMotherDTO(Mother mother) {
		super(mother);
		setName(mother.getName());
		setChildren(mother.getChildren());
		setCivilState(mother.getCivilState().toString());
	}

	@JsonIgnore
	public Mother getModel() {
		Mother model = new Mother();

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


		model.setChildren(getChildren());
		model.setName(getName());
		model.setCivilState(ECivilState.valueOf(civilState));
		return model;
	}
}
