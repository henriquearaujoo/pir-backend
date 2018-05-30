package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import com.samsung.fas.pir.rest.utils.CTools;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Chapter.class)
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterDetailedDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID					uuid;

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
	private 	boolean					status;

	@Getter
	@Setter
	@JsonProperty("greetings")
	private 	GreetingsDTO			greetings;

	@Getter
	@Setter
	@JsonProperty("intervention")
	private 	InterventionDTO			intervention;

	@Getter
	@Setter
	@JsonProperty("conclusion")
	private 	ConclusionDTO			conclusion;

	@Getter
	@Setter
	@JsonProperty("percentage")
	private 	float 					untilComplete;

	@Getter
	@Setter
	@JsonProperty("medias")
	private 	Collection<FileDTO>		medias;

	@Getter
	@Setter
	@JsonProperty("thumbnail")
	private 	Collection<FileDTO>		thumbnails;


	public ChapterDetailedDTO() {
		super();
	}

	public ChapterDetailedDTO(Chapter chapter, boolean detailed) {
		setUuid(chapter.getUuid());
		setChapter(chapter.getChapter());
		setVersion(chapter.getVersion());
		setTitle(chapter.getTitle());
		setSubtitle(chapter.getSubtitle());
		setDescription(chapter.getDescription());
		setContent(chapter.getContent());
		setPurpose(chapter.getPurpose());
		setFamilyTasks(chapter.getFamilyTasks());
		setEstimatedTime(chapter.getEstimatedTime());
		setTimeUntilNext(chapter.getTimeUntilNext()/1000/3600/24);
		setStatus(chapter.isValid());
		setResources(chapter.getResources());
		setUntilComplete(CTools.calculateChapterCompleteness(chapter));
		setGreetings(chapter.getGreetings() != null? new GreetingsDTO(chapter.getGreetings(), true) : null);
		setIntervention(chapter.getIntervention() != null? new InterventionDTO(chapter.getIntervention(), true) : null);
		setConclusion(chapter.getConclusion() != null? new ConclusionDTO(chapter.getConclusion(), true) : null);
		Optional.ofNullable(chapter.getMedias()).ifPresent(item -> setMedias(item.stream().map(FileDTO::new).collect(Collectors.toSet())));
		Optional.ofNullable(chapter.getThumbnails()).ifPresent(item -> setThumbnails(item.stream().map(FileDTO::new).collect(Collectors.toSet())));
	}
}
