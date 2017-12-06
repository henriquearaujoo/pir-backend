package com.samsung.fas.pir.models.dto.chapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.utils.annotations.Number;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class CChapterDTO {
	@ApiObjectField(name="number", order=1)
	@JsonProperty("number")
	@Number(message = "chapter.number.invalid")
	@Min(value = 1, message = "chapter.number.mustbegreater")
	@Getter
	@Setter
	private		int				chapter;

	@ApiObjectField(name="title", order=3, required = true)
	@JsonProperty("title")
	@NotBlank(message = "chapter.title.missing")
	@Getter
	@Setter
	private 	String			title;

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
	@Min(value = 1000 * 60, message = "chapter.estimatedtime.mustbegreater")
	@Getter
	@Setter
	private 	long			estimatedTime;

	@ApiObjectField(name="time_next_visit", order=9, required = true)
	@JsonProperty("time_next_visit")
	@Number(message = "chapter.estimatedtime.invalid")
	@Min(value = 1000 * 60 * 60 * 12, message = "chapter.estimatedtime.mustbegreater")
	@Getter
	@Setter
	private 	long 			timeUntilNext;


	@JsonIgnore
	public Chapter getModel() {
		Chapter e = new Chapter();
		e.setChapter(chapter);
		e.setContent(content);
		e.setDescription(description);
		e.setEstimatedTime(estimatedTime);
		e.setFamilyTasks(familyTasks);
		e.setPurpose(purpose);
		e.setTimeUntilNext(timeUntilNext);
		e.setTitle(title);
		e.setValid(false);
		return e;
	}
}
