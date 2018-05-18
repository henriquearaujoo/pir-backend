package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import com.samsung.fas.pir.rest.utils.CTools;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Chapter.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID			uuid;

	@Getter
	@Setter
	@JsonProperty("number")
	@Min(value = 1, message = "number.greater")
	private		int				chapter;

	@Getter
	@Setter
	@JsonProperty("version")
	@Min(value = 0, message = "version.greater")
	private 	int				version;

	@Getter
	@Setter
	@JsonProperty("title")
	@NotBlank(message = "title.missing")
	private 	String			title;

	@Getter
	@Setter
	@JsonProperty("subtitle")
	@NotBlank(message = "subtitle.missing")
	private 	String			subtitle;

	@Getter
	@Setter
	@JsonProperty("resources")
	private 	String			resources;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String			description;

	@Getter
	@Setter
	@JsonProperty("content")
	@NotBlank(message = "content.missing")
	private		String			content;

	@Getter
	@Setter
	@JsonProperty("goal")
	@NotBlank(message = "goal.missing")
	private		String			purpose;

	@Getter
	@Setter
	@JsonProperty("family_tasks")
	@NotBlank(message = "tasks.missing")
	private		String			familyTasks;

	@Getter
	@Setter
	@JsonProperty("estimated_time")
	@Min(value = 1, message = "estimated.time.greater")
	private 	long			estimatedTime;

	@Getter
	@Setter
	@JsonProperty("time_next_visit")
	@Min(value = 1, message = "time.next.greater")
	private 	long 			timeUntilNext;

	@Getter
	@Setter
	@JsonProperty("status")
	private 	boolean			status;

	@Getter
	@Setter
	@JsonProperty("period")
	@Min(value = 0, message = "invalid.period")
	private 	int				period;

	@Getter
	@Setter
	@JsonProperty("medias")
	@Valid
	private 	Set<FileDTO> 	medias;

	@Getter
	@Setter
	@JsonProperty("thumbnail")
	@Valid
	private 	Set<FileDTO> 	thumbnails;

	@Getter
	@Setter
	@JsonProperty("percentage")
	private 	float 			untilComplete		= 25.0f;

	public ChapterDTO() {
		super();
	}

	public ChapterDTO(Chapter chapter, boolean detailed) {
		setUuid(chapter.getUuid());
		setChapter(chapter.getChapter());
		setPeriod(chapter.getPeriod());
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
		Optional.ofNullable(chapter.getMedias()).ifPresent(item -> setMedias(item.stream().map(FileDTO::new).collect(Collectors.toSet())));
		Optional.ofNullable(chapter.getThumbnails()).ifPresent(item -> setThumbnails(item.stream().map(FileDTO::new).collect(Collectors.toSet())));
	}

	@JsonIgnore
	public Chapter getModel() {
		Chapter e = new Chapter();
		e.setUuid(getUuid());
		e.setVersion(getVersion());
		e.setPeriod(getPeriod());
		e.setChapter(getChapter());
		e.setContent(getContent());
		e.setDescription(getDescription());
		e.setEstimatedTime(getEstimatedTime());
		e.setFamilyTasks(getFamilyTasks());
		e.setPurpose(getPurpose());
		e.setTimeUntilNext(getTimeUntilNext() * 1000 * 3600 * 24);
		e.setTitle(getTitle());
		e.setValid(isStatus());
		e.setResources(getResources());
		e.setSubtitle(getSubtitle());
		e.setMedias(getMedias() != null? getMedias().stream().map(FileDTO::getModel).collect(Collectors.toSet()) : null);
		e.setThumbnails(getThumbnails() != null? getThumbnails().stream().map(FileDTO::getModel).collect(Collectors.toSet()) : null);
		return e;
	}
}
