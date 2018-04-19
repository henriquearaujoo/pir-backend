package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.enums.EChildGender;
import com.samsung.fas.pir.persistence.models.Child;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID 			uuid;

	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String			name;

	@Getter
	@Setter
	@JsonProperty("birth")
	@NotBlank(message = "birth.missing")
	private 	String			birth;

	@Getter
	@Setter
	@JsonProperty("father_name")
	private 	String			fatherName;

	@Getter
	@Setter
	@JsonProperty("gender")
	@NotBlank(message = "gender.missing")
	private 	String 			gender;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_civil_registration")
	private 	boolean			hasCivilRegistration;

	@Getter
	@Setter
	@JsonProperty("civil_reg_justificative")
	private 	String			civilRegistrationJustificative;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_education_diff")
	private 	boolean			hasEducationDifficulty;

	@Getter
	@Setter
	@JsonProperty("education_diff_specification")
	private 	String			educationDifficultySpecification;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("is_premature_born")
	private 	boolean			isPrematureBorn;

	@Getter
	@Setter
	@JsonProperty("born_week")
	@Min(value = 1, message = "invalid.value")
	private 	int				bornWeek;

	@Getter
	@Setter
	@JsonProperty("who_take_care")
	@NotBlank(message = "whotakecare.missing")
	private 	String			whoTakeCare;

	@Getter
	@Setter
	@JsonProperty("plays_with_who")
	@NotBlank(message = "playswithwho.missing")
	private 	String			playsWithWho;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("is_mensaly_weighted")
	private 	boolean			mensalWeight;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("is_in_social_program")
	private 	boolean			isInSocialEducationalPrograms;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("is_vacination_uptodate")
	private 	boolean			vacinationUpToDate;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_relation_diff")
	private 	boolean			hasRelationDifficulties;

	@Getter
	@Setter
	@JsonProperty("mother")
	@Valid
	private		ResponsibleDTO 	mother;

	@Getter
	@Setter
	@JsonProperty("responsible")
	@Valid
	private		ResponsibleDTO 	responsible;

	public ChildDTO() {
		super();
	}

	public ChildDTO(Child child, boolean detailed) {
		setUuid(child.getUuid());
		setName(child.getName());
		setBirth(new SimpleDateFormat("dd-MM-yyyy").format(child.getBirth()));
		setFatherName(child.getFatherName());
		setGender(child.getGender().toString());
		hasCivilRegistration(child.isHasCivilRegistration());
		setCivilRegistrationJustificative(child.getCivilRegistrationJustificative());
		hasEducationDifficulty(child.isHasEducationDifficulty());
		setEducationDifficultySpecification(child.getEducationDifficultySpecification());
		isPrematureBorn(child.isPrematureBorn());
		setBornWeek(child.getBornWeek());
		setWhoTakeCare(child.getWhoTakeCare());
		setPlaysWithWho(child.getPlaysWithWho());
		mensalWeight(child.isMensalWeight());
		isInSocialEducationalPrograms(child.isSocialEducationalPrograms());
		vacinationUpToDate(child.isVacinationUpToDate());
		hasRelationDifficulties(child.isRelationDifficulties());
		setMother(child.getMother() != null? new ResponsibleDTO(child.getMother(), false) : null);
		setResponsible(new ResponsibleDTO(child.getResponsible(), false));
	}

	@JsonIgnore
	public Child getModel() {
		Child model = new Child();

		model.setUuid(getUuid());
		model.setName(getName());
		model.setFatherName(getFatherName());
		model.setGender(EChildGender.valueOf(getGender()));
		model.setHasCivilRegistration(hasCivilRegistration());
		model.setCivilRegistrationJustificative(getCivilRegistrationJustificative());
		model.setHasEducationDifficulty(hasEducationDifficulty());
		model.setEducationDifficultySpecification(getEducationDifficultySpecification());
		model.setPrematureBorn(isPrematureBorn());
		model.setBornWeek(getBornWeek());
		model.setWhoTakeCare(getWhoTakeCare());
		model.setPlaysWithWho(getPlaysWithWho());
		model.setMensalWeight(mensalWeight());
		model.setSocialEducationalPrograms(isInSocialEducationalPrograms());
		model.setVacinationUpToDate(vacinationUpToDate());
		model.setRelationDifficulties(hasRelationDifficulties());

		try {
			model.setBirth(new SimpleDateFormat("dd-MM-yyyy").parse(getBirth()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return model;
	}

}
