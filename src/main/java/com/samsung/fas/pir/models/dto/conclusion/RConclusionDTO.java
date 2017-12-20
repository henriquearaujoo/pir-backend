package com.samsung.fas.pir.models.dto.conclusion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.question.RQuestionDTO;
import com.samsung.fas.pir.models.entity.Conclusion;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Set;
import java.util.stream.Collectors;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RConclusionDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	private		String				id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@Getter
	private 	String				description;

	@ApiObjectField(name="chapter", order=2)
	@JsonProperty("chapter")
	@Getter
	private 	String				chapter;

	@ApiObjectField(name="questions", order=3)
	@JsonProperty("questions")
	@Getter
	private 	Set<RQuestionDTO>	questions;

	private RConclusionDTO(Conclusion e) {
		this.id				= IDCoder.encode(e.getId());
		this.description	= e.getDescription();
		this.chapter		= IDCoder.encode(e.getChapter().getId());
		this.questions		= e.getQuestions().stream().map(RQuestionDTO::toDTO).collect(Collectors.toSet());
	}

	public static RConclusionDTO toDTO(Conclusion e) {
		if (e != null)
			return new RConclusionDTO(e);
		return null;
	}
}