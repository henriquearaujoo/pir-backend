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
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DTO(Chapter.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterDTO extends BaseDTO<Chapter> {
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
	@JsonProperty("extra_forms")
	private		String			additionalForms;

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
	private 	boolean			valid;

	@Getter
	@Setter
	@JsonProperty("period")
	@Min(value = 0, message = "invalid.period")
	private 	int				period;

	@Getter
	@Setter
	@JsonProperty("medias")
	@Valid
	private 	List<FileDTO> mediasDTO;

	@Getter
	@Setter
	@JsonProperty("thumbnail")
	@Valid
	private 	List<FileDTO> 	thumbnailsDTO;

	@Getter
	@Setter
	@JsonProperty("percentage")
	private 	float 			untilComplete		= 25.0f;

	public ChapterDTO() {
		super();
	}

	public ChapterDTO(Chapter chapter, Device device, boolean detailed) {
		super(chapter);
		setTimeUntilNext(chapter.getTimeUntilNext()/1000/3600/24);
		setUntilComplete(CTools.calculateChapterCompleteness(chapter));
		Optional.ofNullable(chapter.getMedias()).ifPresent(item -> setMediasDTO(item.stream().map(FileDTO::new).collect(Collectors.toList())));
		Optional.ofNullable(chapter.getThumbnails()).ifPresent(item -> setThumbnailsDTO(item.stream().map(FileDTO::new).collect(Collectors.toList())));
	}

	@JsonIgnore
	@Override
	public Chapter getModel() {
		Chapter model = super.getModel();
		model.setTimeUntilNext(getTimeUntilNext() * 1000 * 3600 * 24);
		model.setMedias(getMediasDTO() != null? getMediasDTO().stream().map(FileDTO::getModel).collect(Collectors.toSet()) : new ArrayList<>());
		model.setThumbnails(getThumbnailsDTO() != null? getThumbnailsDTO().stream().map(FileDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}
