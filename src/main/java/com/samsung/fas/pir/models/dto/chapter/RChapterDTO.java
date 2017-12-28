package com.samsung.fas.pir.models.dto.chapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.models.entity.Conclusion;
import com.samsung.fas.pir.models.entity.Question;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Set;

@ApiObject
//@JsonInclude(JsonInclude.Include.NON_NULL)
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

	@ApiObjectField(name="subtitle", order=3)
	@JsonProperty("subtitle")
	@Getter
	private 	String			subtitle;

	@ApiObjectField(name="resources", order=3)
	@JsonProperty("resources")
	@Getter
	private 	String			resources;

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

	@ApiObjectField(name="status", order=10)
	@JsonProperty("status")
	@Getter
	private 	boolean			status;

	@ApiObjectField(name="percentage", order=10)
	@JsonProperty("percentage")
	@Getter
	private 	float 			untilComplete		= 25.0f;	// Only chapter info exists

	private RChapterDTO(Chapter entity) {
		Conclusion	c	= entity.getConclusion();

		id				= IDCoder.encode(entity.getId());
		chapter			= entity.getChapter();
		version			= entity.getVersion();
		title			= entity.getTitle();
		subtitle		= entity.getSubtitle();
		description		= entity.getDescription();
		content			= entity.getContent();
		purpose			= entity.getPurpose();
		familyTasks		= entity.getFamilyTasks();
		estimatedTime	= entity.getEstimatedTime();
		timeUntilNext	= entity.getTimeUntilNext();
		status			= entity.isValid();
		resources		= entity.getResources();

		if (c != null) {
			Set<Question> qs = c.getQuestions();
			untilComplete += 12.5f;
			if (qs != null) {
				final int[] questionsWithAnswers = {0};
				qs.forEach(item -> {
					if (item.getAnswers() != null) {
						questionsWithAnswers[0]++;
					}
				});
				if (qs.size() != 0)
					untilComplete += (100 * questionsWithAnswers[0]/qs.size())/12.5f;
			}
		}

		if (entity.getGreetings() != null) {
			untilComplete	+= 25.0f;
		}

		if (entity.getIntervention() != null) {
			untilComplete	+= 25.0f;
		}
	}

	public static RChapterDTO toDTO(Chapter entity) {
		if (entity != null) {
			return new RChapterDTO(entity);
		}
		return null;
	}
}
