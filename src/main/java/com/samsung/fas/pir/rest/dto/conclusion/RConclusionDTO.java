package com.samsung.fas.pir.rest.dto.conclusion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.rest.dto.question.RQuestionDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RConclusionDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	@Setter
	private		String				id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@Getter
	@Setter
	private 	String				description;

	@ApiObjectField(name="chapter", order=2)
	@JsonProperty("chapter")
	@Getter
	@Setter
	private 	String				chapter;

	@ApiObjectField(name="questions", order=3)
	@JsonProperty("questions")
	@Getter
	@Setter
	private 	Set<RQuestionDTO>	questions;

	private RConclusionDTO(Conclusion e) {
		setId(IDCoder.encode(e.getUuid()));
		setDescription(e.getDescription());
		setChapter(IDCoder.encode(e.getChapter().getUuid()));
		setQuestions(e.getQuestions() != null? e.getQuestions().stream().map(RQuestionDTO::toDTO).collect(Collectors.toSet()) : null);
	}

	public static RConclusionDTO toDTO(Conclusion e) {
		if (e != null)
			return new RConclusionDTO(e);
		return null;
	}
}