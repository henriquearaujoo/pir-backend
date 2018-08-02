package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.persistence.models.Visit;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Visit.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDTO extends BaseDTO<Visit> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private 		long						mobileId;

	@Getter
	@Setter
	@JsonProperty("done_at")
	private 		Date						doneAt;

	@Getter
	@Setter
	@JsonProperty("number")
	private 		int							number;

	@Getter
	@Setter
	@JsonProperty("family_rating")
	private 		short						familyRating;

	@Getter
	@Setter
	@JsonProperty("agent_rating")
	private 		short						agentRating;

	@Getter
	@Setter
	@JsonProperty("visit_time")
	private 		long						duration;

	@Getter
	@Setter
	@JsonProperty(value = "chapter_id")
	private 		UUID						chapterUUID;

	@Getter
	@Setter
	@JsonProperty(value = "form_id")
	private 		UUID						formUUID;

	@Getter
	@Setter
	@JsonProperty(value = "answers")
	@Valid
	private 		Collection<AnswerDTO>		answersDTO;

	@Getter
	@Setter
	@JsonProperty(value = "pregnancy")
	private 		PregnancyDTO				pregnancyDTO;

	@Getter
	@Setter
	@JsonProperty(value = "chapter")
	private 		ChapterDTO					chapterDTO;

	@Getter
	@Setter
	@JsonProperty(value = "child")
	private 		ChildDTO					childDTO;

	public VisitDTO() {
		super();
	}

	public VisitDTO(Visit visit, Device device, boolean detailed) {
		super(visit);
		setFormUUID(visit.getForm() != null? visit.getForm().getUuid() : null);
		setAnswersDTO(visit.getAnswers() != null? visit.getAnswers().stream().map(answer -> new AnswerDTO(answer, null, true)).collect(Collectors.toList()) : null);
		setChapterDTO(new ChapterDTO(visit.getChapter(), device, false));
		setChildDTO(detailed && visit.getChild() != null? new ChildDTO(visit.getChild(), device, false) : null);
		setPregnancyDTO(detailed && visit.getPregnancy() != null? new PregnancyDTO(visit.getPregnancy(), device, false) : null);
		setChapterUUID(visit.getChapter().getUuid());
	}

	@JsonIgnore
	@Override
	public Visit getModel() {
		Visit 	model 	= super.getModel();
		Chapter	chapter	= new Chapter();
		Form	form	= new Form();
		chapter.setUuid(getChapterUUID());
		form.setUuid(getFormUUID());
		model.setChapter(chapter);
		model.setForm(form);
		model.setPregnancy(getPregnancyDTO() != null? getPregnancyDTO().getModel() : null);
		model.setChild(getChildDTO() != null? getChildDTO().getModel() : null);
		model.setAnswers(getAnswersDTO() != null? getAnswersDTO().stream().map(AnswerDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}
