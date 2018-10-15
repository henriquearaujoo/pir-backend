package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import com.samsung.fas.pir.rest.utils.CTools;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@DTO(Chapter.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterDTO extends BaseDTO<Chapter> {
	@Getter
	@Setter
	@JsonProperty("number")
	private		int						chapter;

	@Getter
	@Setter
	@JsonProperty("version")
	private 	int						version;

	@Getter
	@Setter
	@JsonProperty("title")
	private 	String					title;

	@Getter
	@Setter
	@JsonProperty("subtitle")
	private 	String					subtitle;

	@Getter
	@Setter
	@JsonProperty("resources")
	private 	String					resources;

	@Getter
	@Setter
	@JsonProperty("description")
	private 	String					description;

	@Getter
	@Setter
	@JsonProperty("extra_forms")
	private		String					additionalForms;

	@Getter
	@Setter
	@JsonProperty("content")
	private		String					content;

	@Getter
	@Setter
	@JsonProperty("goal")
	private		String					purpose;

	@Getter
	@Setter
	@JsonProperty("family_tasks")
	private		String					familyTasks;

	@Getter
	@Setter
	@JsonProperty("estimated_time")
	private 	long					estimatedTime;

	@Getter
	@Setter
	@JsonProperty("time_next_visit")
	private 	long 					timeUntilNext;

	@Getter
	@Setter
	@JsonProperty("status")
	private 	boolean					valid;

	@Getter
	@Setter
	@JsonProperty("period")
	private 	int						period;

	@Getter
	@Setter
	@JsonProperty("greetings")
	private 	GreetingsDTO			greetingsDTO;

	@Getter
	@Setter
	@JsonProperty("intervention")
	private 	InterventionDTO			interventionDTO;

	@Getter
	@Setter
	@JsonProperty("conclusion")
	private 	ConclusionDTO			conclusionDTO;

	@Getter
	@Setter
	@JsonProperty("percentage")
	private 	float 					untilComplete;

	@Getter
	@Setter
	@JsonProperty("medias")
	private Collection<FileDTO> 		mediasDTO;

	public ChapterDTO() {
		super();
	}

	public ChapterDTO(Chapter chapter, Device device, boolean detailed) {
		super(chapter);
		if (!device.isNormal()) {
			setGreetingsDTO(chapter.getGreetings() != null? new GreetingsDTO(chapter.getGreetings(), device, true) : null);
			setInterventionDTO(chapter.getIntervention() != null? new InterventionDTO(chapter.getIntervention(), device, true) : null);
			setConclusionDTO(chapter.getConclusion() != null? new ConclusionDTO(chapter.getConclusion(), device, true) : null);
			setMediasDTO(chapter.getMedias().stream().map(FileDTO::new).collect(Collectors.toList()));
		} else {
			setTimeUntilNext(chapter.getTimeUntilNext() / 1000 / 3600 / 24);
			setUntilComplete(CTools.calculateChapterCompleteness(chapter));
			setMediasDTO(chapter.getMedias().stream().map(FileDTO::new).collect(Collectors.toList()));
		}
	}

	@JsonIgnore
	@Override
	public Chapter getModel() {
		Chapter model = super.getModel();
		model.setTimeUntilNext(getTimeUntilNext() * 1000 * 3600 * 24);
		model.setMedias(getMediasDTO() != null? getMediasDTO().stream().map(FileDTO::getModel).collect(Collectors.toSet()) : new ArrayList<>());
		return model;
	}
}
