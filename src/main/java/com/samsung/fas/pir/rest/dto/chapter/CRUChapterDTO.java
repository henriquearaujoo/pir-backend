package com.samsung.fas.pir.rest.dto.chapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.rest.dto.FileDTO;
import com.samsung.fas.pir.utils.IDCoder;
import com.samsung.fas.pir.utils.Tools;
import com.samsung.fas.pir.utils.annotations.Number;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRUChapterDTO {
	@Getter
	@Setter
	private		String			id;

	@Getter
	@Setter
	@JsonProperty("number")
	@Number(message = "chapter.number.invalid")
	@Min(value = 1, message = "number.greater")
	private		int				chapter;

	@Getter
	@Setter
	@JsonProperty("version")
	@Number(message = "chapter.version.invalid")
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
	@NotBlank(message = "resources.missing")
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

	public CRUChapterDTO() {
		super();
	}

	public CRUChapterDTO(Chapter chapter, boolean detailed) {
		setId(IDCoder.encode(chapter.getUuid()));
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
		setUntilComplete(Tools.calculate(chapter));
		Optional.ofNullable(chapter.getMedias()).ifPresent(item -> setMedias(item.stream().map(FileDTO::toDTO).collect(Collectors.toSet())));
		Optional.ofNullable(chapter.getThumbnails()).ifPresent(item -> setThumbnails(item.stream().map(FileDTO::toDTO).collect(Collectors.toSet())));
	}

	@JsonIgnore
	public Chapter getModel() {
		Chapter e = new Chapter();
		e.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		e.setVersion(getVersion());
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
