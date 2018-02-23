package com.samsung.fas.pir.rest.dto.child;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Child;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CRUChildDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String 			id;

	@Getter
	@Setter
	@JsonProperty("responsible_id")
	@NotBlank(message = "responsible.id.missing")
	private 	String			responsibleID;

	@Getter
	@Setter
	@JsonProperty("community_id")
	@NotBlank(message = "community.id.missing")
	private 	String			communityID;

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
	@JsonProperty("mother_name")
	@NotBlank(message = "mother.name.missing")
	private 	String			motherName;

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

	@JsonIgnore
	public Child getModel() {
		Child model = new Child();

		model.setUuid(getId() != null && !getId().replaceAll("\\+", "").isEmpty()? IDCoder.decode(getId()) : null);
//		model.

		return model;
	}

}
