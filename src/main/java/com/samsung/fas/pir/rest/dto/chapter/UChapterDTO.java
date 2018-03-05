package com.samsung.fas.pir.rest.dto.chapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.rest.dto.FileDTO;
import com.samsung.fas.pir.utils.IDCoder;
import com.samsung.fas.pir.utils.annotations.Number;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UChapterDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@NotBlank(message = "chapter.id.invalid")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="number", order=1)
	@JsonProperty("number")
	@Number(message = "chapter.number.invalid")
	@Min(value = 1, message = "chapter.number.mustbegreater")
	@Getter
	@Setter
	private		int				chapter;

	@ApiObjectField(name="version", order=2)
	@JsonProperty("version")
	@Number(message = "chapter.version.invalid")
	@Min(value = 0, message = "chapter.version.mustbegreater")
	@Getter
	@Setter
	private 	int				version;

	@ApiObjectField(name="title", order=3, required = true)
	@JsonProperty("title")
	@NotBlank(message = "chapter.title.missing")
	@Getter
	@Setter
	private 	String			title;

	@ApiObjectField(name="subtitle", order=3, required = true)
	@JsonProperty("subtitle")
	@NotBlank(message = "chapter.subtitle.missing")
	@Getter
	@Setter
	private 	String			subtitle;

	@ApiObjectField(name="resources", order=3, required = true)
	@JsonProperty("resources")
	@NotBlank(message = "chapter.resources.missing")
	@Getter
	@Setter
	private 	String			resources;

	@ApiObjectField(name="description", order=4, required = true)
	@JsonProperty("description")
	@NotBlank(message = "chapter.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="content", order=5, required = true)
	@JsonProperty("content")
	@NotBlank(message = "chapter.content.missing")
	@Getter
	@Setter
	private		String			content;

	@ApiObjectField(name="goal", order=6, required = true)
	@JsonProperty("goal")
	@NotBlank(message = "chapter.goal.missing")
	@Getter
	@Setter
	private		String			purpose;

	@ApiObjectField(name="family_tasks", order=7, required = true)
	@JsonProperty("family_tasks")
	@NotBlank(message = "chapter.tasks.missing")
	@Getter
	@Setter
	private		String			familyTasks;

	@ApiObjectField(name="estimated_time", order=8, required = true)
	@JsonProperty("estimated_time")
	@Number(message = "chapter.estimatedtime.invalid")
	@Getter
	@Setter
	private 	long			estimatedTime;

	@ApiObjectField(name="time_next_visit", order=9, required = true)
	@JsonProperty("time_next_visit")
	@Number(message = "chapter.estimatedtime.invalid")
	@Min(value = 1, message = "chapter.timetonext.mustbegreater")
	@Getter
	@Setter
	private 	long 			timeUntilNext;

	@ApiObjectField(name="status", order=10, required = true)
	@JsonProperty("status")
	@Getter
	@Setter
	private 	boolean			status;

	@ApiObjectField(name="medias", order=11, required = true)
	@JsonProperty("medias")
	@Getter
	@Setter
	@Valid
	private 	Set<FileDTO> 	medias;

	@ApiObjectField(name="thumbnail", order=11, required = true)
	@JsonProperty("thumbnail")
	@Getter
	@Setter
	@Valid
	private 	Set<FileDTO> 	thumbnails;

	@JsonIgnore
	public Chapter getModel() {
		Chapter e = new Chapter();
		e.setUuid(IDCoder.decode(getId()));
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
