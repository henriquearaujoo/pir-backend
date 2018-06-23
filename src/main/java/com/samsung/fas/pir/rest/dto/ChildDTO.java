package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.models.Child;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Child.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID 						uuid;

	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long						tempID;

	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String						name;

	@Getter
	@Setter
	@JsonProperty("birth")
	@NotBlank(message = "birth.missing")
	private 	String						birth;

	@Getter
	@Setter
	@JsonProperty("father_name")
	private 	String						fatherName;

	@Getter
	@Setter
	@JsonProperty("gender")
	@NotNull(message = "gender.missing")
	private 	EGender 					gender;

	@Getter
	@Setter
	@JsonProperty("has_civil_registration")
	private 	boolean						hasCivilRegistration;

	@Getter
	@Setter
	@JsonProperty("civil_reg_justificative")
	private 	String 						civilRegistrationJustification;

	@Getter
	@Setter
	@JsonProperty("has_education_diff")
	private 	boolean						hasEducationDifficulty;

	@Getter
	@Setter
	@JsonProperty("education_diff_specification")
	private 	String						educationDifficultySpecification;

	@Getter
	@Setter
	@JsonProperty("is_premature_born")
	private 	boolean						isPrematureBorn;

	@Getter
	@Setter
	@JsonProperty("born_week")
	@Min(value = 0, message = "invalid.value")
	private 	int							bornWeek;

	@Getter
	@Setter
	@JsonProperty("who_take_care")
	@NotBlank(message = "whotakecare.missing")
	private 	String						whoTakeCare;

	@Getter
	@Setter
	@JsonProperty("plays_with_who")
	@NotBlank(message = "playswithwho.missing")
	private 	String						playsWithWho;

	@Getter
	@Setter
	@JsonProperty("is_mensaly_weighted")
	private 	boolean						monthlyWeight;

	@Getter
	@Setter
	@JsonProperty("is_in_social_program")
	private 	boolean						inSocialEducationalPrograms;

	@Getter
	@Setter
	@JsonProperty("is_vacination_uptodate")
	private 	boolean						vaccinationUpToDate;

	@Getter
	@Setter
	@JsonProperty("has_relation_diff")
	private 	boolean						hasRelationDifficulties;

	@Getter
	@Setter
	@JsonProperty("agent_id")
	private		UUID						agentID;

	@Getter
	@Setter
	@JsonProperty("mother")
	private		ResponsibleDTO 				mother;

	@Getter
	@Setter
	@JsonProperty("visits")
	@Valid
	private		List<VisitDTO>				visits;

	@Getter
	@Setter
	@JsonProperty("responsible")
	private 	List<ResponsibleDTO> 		responsible;

	public ChildDTO() {
		super();
	}

	public ChildDTO(Child child, Device device, boolean detailed) {
		setTempID(child.getMobileId());
		setUuid(child.getUuid());
		setName(child.getName());
		setBirth(new SimpleDateFormat("dd-MM-yyyy").format(child.getBirth()));
		setFatherName(child.getFatherName());
		setGender(child.getGender());
		setHasCivilRegistration(child.isHasCivilRegistration());
		setCivilRegistrationJustification(child.getCivilRegistrationJustification());
		setHasEducationDifficulty(child.isHasEducationDifficulty());
		setEducationDifficultySpecification(child.getEducationDifficultySpecification());
		setPrematureBorn(child.isPrematureBorn());
		setBornWeek(child.getBornWeek());
		setWhoTakeCare(child.getWhoTakeCare());
		setPlaysWithWho(child.getPlaysWithWho());
		setMonthlyWeight(child.isMonthlyWeighted());
		setInSocialEducationalPrograms(child.isSocialEducationalPrograms());
		setVaccinationUpToDate(child.isVaccinationUpToDate());
		setHasRelationDifficulties(child.isRelationDifficulties());
		setResponsible(detailed? child.getResponsible().stream().map(responsible -> new ResponsibleDTO(responsible, device, false)).collect(Collectors.toList()) : null);
		setVisits(child.getVisits().stream().map(item -> new VisitDTO(item, device, false)).collect(Collectors.toList()));
	}

	@JsonIgnore
	public Child getModel() {
		Child model = new Child();
		model.setMobileId(getTempID());
		model.setUuid(getUuid());
		model.setName(getName());
		model.setFatherName(getFatherName());
		model.setGender(getGender());
		model.setHasCivilRegistration(isHasCivilRegistration());
		model.setCivilRegistrationJustification(getCivilRegistrationJustification());
		model.setHasEducationDifficulty(isHasEducationDifficulty());
		model.setEducationDifficultySpecification(getEducationDifficultySpecification());
		model.setPrematureBorn(isPrematureBorn());
		model.setBornWeek(getBornWeek());
		model.setWhoTakeCare(getWhoTakeCare());
		model.setPlaysWithWho(getPlaysWithWho());
		model.setMonthlyWeighted(isMonthlyWeight());
		model.setSocialEducationalPrograms(isInSocialEducationalPrograms());
		model.setVaccinationUpToDate(isVaccinationUpToDate());
		model.setRelationDifficulties(isHasRelationDifficulties());
		model.setResponsible(getResponsible() != null? getResponsible().stream().map(ResponsibleDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setVisits(getVisits() != null? getVisits().stream().map(VisitDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());

		try {
			model.setBirth(new SimpleDateFormat("dd-MM-yyyy").parse(getBirth()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return model;
	}

}
