package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Child.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildDTO extends BaseDTO<Child> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long						mobileId;

	@Getter
	@Setter
	@JsonProperty("name")
	@NotBlank(message = "name.missing")
	private 	String						name;

	@Getter
	@Setter
	@JsonProperty("birth")
	@NotBlank(message = "birth.missing")
	private 	String						birthDate;

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
	private 	boolean						prematureBorn;

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
	private 	boolean						monthlyWeighted;

	@Getter
	@Setter
	@JsonProperty("is_in_social_program")
	private 	boolean						socialEducationalPrograms;

	@Getter
	@Setter
	@JsonProperty("is_vacination_uptodate")
	private 	boolean						vaccinationUpToDate;

	@Getter
	@Setter
	@JsonProperty("has_relation_diff")
	private 	boolean						relationDifficulties;

	@Getter
	@Setter
	@JsonProperty("agent_id")
	private		UUID						agentUUID;

	@Getter
	@Setter
	@JsonProperty("answers")
	@Valid
	private		List<SAnswerDTO>			answersDTO;

	@Getter
	@Setter
	@JsonProperty("visits")
	@Valid
	private		List<VisitDTO>				visitsDTO;

	@Getter
	@Setter
	@JsonProperty("responsible")
	private 	List<FamilyDTO> familyDTO;

	public ChildDTO() {
		super();
	}

	public ChildDTO(Child child, Device device, boolean detailed) {
		super(child);
		setBirthDate(new SimpleDateFormat("dd-MM-yyyy").format(child.getBirth()));
//		setFamilyDTO(detailed? child.getResponsible().stream().map(responsible -> new FamilyDTO(responsible, device, false)).collect(Collectors.toList()) : null);
		setAnswersDTO(child.getAnswers().stream().map(item -> new SAnswerDTO(item, device, false)).collect(Collectors.toList()));
		setVisitsDTO(child.getVisits().stream().map(item -> new VisitDTO(item, device, false)).collect(Collectors.toList()));
	}

	@JsonIgnore
	@Override
	public Child getModel() {
		Child model = super.getModel();
//		model.setResponsible(getFamilyDTO() != null? getFamilyDTO().stream().map(FamilyDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setAnswers(getAnswersDTO() != null? getAnswersDTO().stream().map(SAnswerDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setVisits(getVisitsDTO() != null? getVisitsDTO().stream().map(VisitDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		try {
			model.setBirth(new SimpleDateFormat("dd-MM-yyyy").parse(getBirthDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return model;
	}

}
