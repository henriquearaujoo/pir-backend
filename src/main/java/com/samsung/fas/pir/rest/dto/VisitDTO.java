package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Visit;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Visit.class)
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDTO {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private 		long		tempID;

	@Getter
	@Setter
	@JsonProperty("id")
	private 		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("number")
	private 		int			number;

	@Getter
	@Setter
	@JsonProperty("family_rating")
	private 		short		familyRating;

	@Getter
	@Setter
	@JsonProperty("agent_rating")
	private 		short		agentRating;

	@Getter
	@Setter
	@JsonProperty("done_at")
	private 		Date		doneAt;

	@Getter
	@Setter
	@JsonProperty("visit_time")
	private 		long		duration;

	@Getter
	@Setter
	@JsonProperty(value = "agent_id")
	private 		UUID		agentUUID;

	@Getter
	@Setter
	@JsonProperty(value = "chapter_id")
	private 		UUID		chapterUUID;

	@Getter
	@Setter
	@JsonProperty(value = "form_id")
	private 		UUID		formUUID;

	@Getter
	@Setter
	@JsonProperty(value = "responsible")
	@Valid
	private 		ResponsibleDTO				responsible;

	@Getter
	@Setter
	@JsonProperty(value = "child")
	@Valid
	private 		ChildDTO					child;

	@Getter
	@Setter
	@JsonProperty(value = "answers")
	@Valid
	private 		Collection<AnswerDTO>		answers;

	public VisitDTO() {
		super();
	}

	public VisitDTO(Visit visit, boolean detailed) {
		setUuid(visit.getUuid());
		setNumber(visit.getNumber());
		setFamilyRating(visit.getFamilyRating());
		setAgentRating(visit.getAgentRating());
		setDoneAt(visit.getDoneAt());
		setDuration(visit.getDuration());
		setTempID(visit.getMobileId());

		setAnswers(visit.getAnswers() != null? visit.getAnswers().stream().map(answer -> new AnswerDTO(answer, true)).collect(Collectors.toList()) : null);
		setAgentUUID(visit.getAgent().getUuid());
		setChapterUUID(visit.getChapter().getUuid());
		setFormUUID(visit.getForm() != null? visit.getForm().getUuid() : null);
		setChild(visit.getChild() != null? new ChildDTO(visit.getChild(), false) : null);
//		setResponsible(visit.getResponsible() != null? new ResponsibleDTO(visit.getResponsible(), false) : null);
	}

	@JsonIgnore
	public Visit getModel() {
		Visit model = new Visit();

		model.setMobileId(getTempID());
		model.setUuid(getUuid());
		model.setNumber(getNumber());
		model.setFamilyRating(getFamilyRating());
		model.setAgentRating(getAgentRating());
		model.setDoneAt(getDoneAt());
		model.setDuration(getDuration());
//		model.setResponsible(getResponsible() != null? getResponsible().getModel() : null);
		model.setChild(getChild() != null? getChild().getModel() : null);
//		model.setAnswers(getAnswers() != null? getAnswers().stream().map(AnswerDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());

		return model;
	}
}
