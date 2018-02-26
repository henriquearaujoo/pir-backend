package com.samsung.fas.pir.rest.dto.chapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.rest.dto.FileDTO;
import com.samsung.fas.pir.utils.IDCoder;
import com.samsung.fas.pir.utils.Tools;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApiObject
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class RChapterDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="number", order=1)
	@JsonProperty("number")
	@Getter
	@Setter
	private		int				chapter;

	@ApiObjectField(name="version", order=2)
	@JsonProperty("version")
	@Getter
	@Setter
	private 	int				version;

	@ApiObjectField(name="title", order=3)
	@JsonProperty("title")
	@Getter
	@Setter
	private 	String			title;

	@ApiObjectField(name="subtitle", order=3)
	@JsonProperty("subtitle")
	@Getter
	@Setter
	private 	String			subtitle;

	@ApiObjectField(name="resources", order=3)
	@JsonProperty("resources")
	@Getter
	@Setter
	private 	String			resources;

	@ApiObjectField(name="description", order=4)
	@JsonProperty("description")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="content", order=5)
	@JsonProperty("content")
	@Getter
	@Setter
	private		String			content;

	@ApiObjectField(name="goal", order=6)
	@JsonProperty("goal")
	@Getter
	@Setter
	private		String			purpose;

	@ApiObjectField(name="family_tasks", order=7)
	@JsonProperty("family_tasks")
	@Getter
	@Setter
	private		String			familyTasks;

	@ApiObjectField(name="estimated_time", order=8)
	@JsonProperty("estimated_time")
	@Getter
	@Setter
	private 	long			estimatedTime;

	@ApiObjectField(name="time_next_visit", order=9)
	@JsonProperty("time_next_visit")
	@Getter
	@Setter
	private 	long 			timeUntilNext;

	@ApiObjectField(name="status", order=10)
	@JsonProperty("status")
	@Getter
	@Setter
	private 	boolean			status;

	@ApiObjectField(name="percentage", order=10)
	@JsonProperty("percentage")
	@Getter
	@Setter
	private 	float 			untilComplete		= 25.0f;	// Only chapter info exists

	@ApiObjectField(name="medias", order=10)
	@JsonProperty("medias")
	@Getter
	@Setter
	private 	Set<FileDTO>	medias;

	@ApiObjectField(name="thumbnails", order=10)
	@JsonProperty("thumbnails")
	@Getter
	@Setter
	private 	Set<FileDTO>	thumbnails;


	private RChapterDTO(Chapter entity) {
		Conclusion	c	= entity.getConclusion();
		setId(IDCoder.encode(entity.getUuid()));
		setChapter(entity.getChapter());
		setVersion(entity.getVersion());
		setTitle(entity.getTitle());
		setSubtitle(entity.getSubtitle());
		setDescription(entity.getDescription());
		setContent(entity.getContent());
		setPurpose(entity.getPurpose());
		setFamilyTasks(entity.getFamilyTasks());
		setEstimatedTime(entity.getEstimatedTime());
		setTimeUntilNext(entity.getTimeUntilNext()/1000/3600/24);
		setStatus(entity.isValid());
		setResources(entity.getResources());
		setUntilComplete(Tools.calculate(entity));
		Optional.of(entity.getMedias()).ifPresent(item -> setMedias(item.stream().map(FileDTO::toDTO).collect(Collectors.toSet())));
		Optional.of(entity.getThumbnails()).ifPresent(item -> setThumbnails(item.stream().map(FileDTO::toDTO).collect(Collectors.toSet())));
	}

	public static RChapterDTO toDTO(Chapter entity) {
		if (entity != null) {
			return new RChapterDTO(entity);
		}
		return null;
	}
}
