package com.samsung.fas.pir.models.dto.chapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RChapterDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	private		String			id;

	@ApiObjectField(name="number", order=1)
	@JsonProperty("number")
	@Getter
	private		int				chapter;

	@ApiObjectField(name="version", order=2)
	@JsonProperty("version")
	@Getter
	private 	int				version;

	@ApiObjectField(name="title", order=3)
	@JsonProperty("title")
	@Getter
	private 	String			title;

	@ApiObjectField(name="description", order=4)
	@JsonProperty("description")
	@Getter
	private 	String			description;

	@ApiObjectField(name="content", order=5)
	@JsonProperty("content")
	@Getter
	private		String			content;

	@ApiObjectField(name="goal", order=6)
	@JsonProperty("goal")
	@Getter
	private		String			purpose;

	@ApiObjectField(name="family_tasks", order=7)
	@JsonProperty("family_tasks")
	@Getter
	private		String			familyTasks;

	@ApiObjectField(name="estimated_time", order=8)
	@JsonProperty("estimated_time")
	@Getter
	private 	long			estimatedTime;

	@ApiObjectField(name="time_next_visit", order=9)
	@JsonProperty("time_next_visit")
	@Getter
	private 	long 			timeUntilNext;

	@ApiObjectField(name="enable", order=10)
	@JsonProperty("enable")
	@Getter
	private 	boolean			status;

	private RChapterDTO(Chapter entity) {
		id				= IDCoder.encode(entity.getId());
		chapter			= entity.getChapter();
		version			= entity.getVersion();
		title			= entity.getTitle();
		description		= entity.getDescription();
		content			= entity.getContent();
		purpose			= entity.getPurpose();
		familyTasks		= entity.getFamilyTasks();
		estimatedTime	= entity.getEstimatedTime();
		timeUntilNext	= entity.getTimeUntilNext();
		status			= entity.isValid();
	}

	public static RChapterDTO toDTO(Chapter entity) {
		if (entity != null) {
			return new RChapterDTO(entity);
		}
		return null;
	}
}
