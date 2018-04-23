package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Answer;
import com.samsung.fas.pir.persistence.models.Visit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDTO {
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







	@Getter(onMethod = @__(@JsonIgnore))
	@Setter
	@JsonProperty(value = "agent_id", access = JsonProperty.Access.WRITE_ONLY)
	private 		UUID		agentUUID;

	@Getter(onMethod = @__(@JsonIgnore))
	@Setter
	@JsonProperty(value = "chapter_id", access = JsonProperty.Access.WRITE_ONLY)
	private 		UUID		chapterUUID;

	@Getter(onMethod = @__(@JsonIgnore))
	@Setter
	@JsonProperty(value = "form_id", access = JsonProperty.Access.WRITE_ONLY)
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






	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter(onMethod = @__(@JsonIgnore))
	@JsonProperty(value = "agent")
	private 		UserDTO		agent;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter(onMethod = @__(@JsonIgnore))
	@JsonProperty(value = "chapter")
	private 		ChapterDTO	chapter;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter(onMethod = @__(@JsonIgnore))
	@JsonProperty(value = "form")
	private 		FormDTO		form;






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

//		setAgent(new UserDTO(visit.getAgent(), false));
		setChapter(new ChapterDTO(visit.getChapter(), false));
		setForm(new FormDTO(visit.getForm(), false));
		setChild(visit.getChild() != null? new ChildDTO(visit.getChild(), false) : null);
		setResponsible(visit.getResponsible() != null? new ResponsibleDTO(visit.getResponsible(), false) : null);
	}

	@JsonIgnore
	public Visit getModel() {
		Visit model = new Visit();

		model.setUuid(getUuid());
		model.setNumber(getNumber());
		model.setFamilyRating(getFamilyRating());
		model.setAgentRating(getAgentRating());
		model.setDoneAt(getDoneAt());
		model.setDuration(getDuration());
		model.setResponsible(getResponsible() != null? getResponsible().getModel() : null);
		model.setChild(getChild() != null? getChild().getModel() : null);
		model.setAnswers(getAnswers() != null? getAnswers().stream().map(AnswerDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());

		return model;
	}
}
