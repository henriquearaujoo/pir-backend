package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Visit;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Visit.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitFrontDTO extends BaseDTO<Visit> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private 		long				mobileId;

	@Getter
	@Setter
	@JsonProperty("done_at")
	private 		Date				doneAt;

	@Getter
	@Setter
	@JsonProperty("number")
	private 		int					number;

	@Getter
	@Setter
	@JsonProperty("family_rating")
	private 		short				familyRating;

	@Getter
	@Setter
	@JsonProperty("agent_rating")
	private 		short				agentRating;

	@Getter
	@Setter
	@JsonProperty("visit_time")
	private 		long				duration;

	@Getter
	@Setter
	@JsonProperty(value = "agent")
	private 		UserDTO				agentDTO;

	@Getter
	@Setter
	@JsonProperty(value = "chapter")
	private 		ChapterDTO			chapterDTO;

	@Getter
	@Setter
	@JsonProperty(value = "form_id")
	private 		UUID				formUUID;

	@Getter
	@Setter
	@JsonProperty(value = "child")
	@Valid
	private 		ChildDTO			childDTO;

	@Getter
	@Setter
	@JsonProperty(value = "responsible")
	@Valid
	private 		ResponsibleDTO		responsibleDTO;

	@Getter
	@Setter
	@JsonProperty(value = "answers")
	@Valid
	private 		List<AnswerDTO> 	answersDTO;

	public VisitFrontDTO() {
		super();
	}

	public VisitFrontDTO(Visit visit, Device device, boolean detailed) {
		super(visit);
		setAnswersDTO(visit.getAnswers() != null? visit.getAnswers().stream().map(answer -> new AnswerDTO(answer, device,true)).collect(Collectors.toList()) : null);
		setAgentDTO(new UserDTO(visit.getAgent(), device, false));
		setChapterDTO(new ChapterDTO(visit.getChapter(), device, false));
		setFormUUID(visit.getForm() != null? visit.getForm().getUuid() : null);
		setChildDTO(visit.getChild() != null? new ChildDTO(visit.getChild(), device, false) : null);
	}
}