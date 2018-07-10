package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.Responsible;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Responsible.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsibleDTO extends BaseDTO<Responsible> {
	// region Properties
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long				mobileId;

	@Getter
	@Setter
	@JsonProperty("agent_id")
	private 	UUID 				agentUUID;

	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String				name;

	@Getter
	@Setter
	@JsonProperty("birth")
	@NotEmpty(message = "date.missing")
	@JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
	private 	String 				birthDate;

	@Getter
	@Setter
	@JsonProperty("in_social_program")
	private 	boolean				inSocialProgram;

	@Getter
	@Setter
	@JsonProperty("habitation_type")
	private 	EHabitationType		habitationType;

	@Getter
	@Setter
	@JsonProperty("habitation_members_count")
	private 	int					habitationMembersCount;

	@Getter
	@Setter
	@JsonProperty("live_with")
	private 	String				liveWith;

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
	@JsonProperty("income_participation")
	private 	String				incomeParticipation;

	@Getter
	@Setter
	@JsonProperty("gender")
	private 	EGender 			gender;

	@Getter
	@Setter
	@JsonProperty("children_count")
	private 	long				childrenCount;

	@Getter
	@Setter
	@JsonProperty("civil_state")
	private 	ECivilState			civilState;

	@Getter
	@Setter
	@JsonProperty("drinking_water_treatment")
	private 	String				drinkingWaterTreatment;

	@Getter
	@Setter
	@JsonProperty("has_hospital_nearby")
	private 	boolean				hasHospital;

	@Getter
	@Setter
	@JsonProperty("has_sanitation")
	private 	boolean				hasSanitation;

	@Getter
	@Setter
	@JsonProperty("has_water_treatment")
	private 	boolean				hasWaterTreatment;

	@Getter
	@Setter
	@JsonProperty("has_other_children")
	private 	boolean				familyHasChildren;

	@Getter
	@Setter
	@JsonProperty("observations")
	private 	String				observations;

	@Getter
	@Setter
	@JsonProperty("is_pregnant")
	private		boolean				pregnant;
	// endregion

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
	@JsonProperty("pregnancies")
	private 	List<PregnancyDTO> 	pregnanciesDTO;

	@Getter
	@Setter
	@JsonProperty("community")
	private 	CommunityDTO		communityDTO;

	public ResponsibleDTO() {
		super();
	}

	public ResponsibleDTO(Responsible responsible, Device device, boolean detailed) {
		super(responsible);
		setBirthDate(new SimpleDateFormat("dd-MM-yyyy").format(responsible.getBirth()));
		setPregnanciesDTO(responsible.getPregnancies().stream().map(item -> new PregnancyDTO(item, device, false)).collect(Collectors.toList()));
		setChildrenDTO(responsible.getChildren().stream().map(item -> new ChildDTO(item, device, false)).collect(Collectors.toList()));
		setCommunityDTO(device != null && device.isNormal()? new CommunityDTO(responsible.getCommunity(), device, false) : null);
	}

	@JsonIgnore
	@Override
	public Responsible getModel() {
		Responsible model = super.getModel();
		model.setCommunity(getCommunityDTO() != null? getCommunityDTO().getModel() : null);
		model.setChildren(getChildrenDTO() != null? getChildrenDTO().stream().map(ChildDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setPregnancies(getPregnanciesDTO() != null? getPregnanciesDTO().stream().map(PregnancyDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		try {
			model.setBirth(new SimpleDateFormat("dd-MM-yyyy").parse(getBirthDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return model;
	}
}