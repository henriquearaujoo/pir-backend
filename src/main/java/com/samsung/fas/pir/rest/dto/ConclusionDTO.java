package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Conclusion;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Conclusion.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConclusionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("chapter_id")
	private 	UUID 		chapterUUID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "chapter.greetings.description.missing")
	private 	String		description;

	@Getter
	@Setter
	@JsonProperty("questions")
	private 	Collection<QuestionDTO>		questions;

	public ConclusionDTO() {
		super();
	}

	public ConclusionDTO(Conclusion conclusion, boolean detailed) {
		setUuid(conclusion.getUuid());
		setDescription(conclusion.getDescription());
		setChapterUUID(conclusion.getChapter().getUuid());
		setQuestions(conclusion.getQuestions() != null? conclusion.getQuestions().stream().map(item -> new QuestionDTO(item, false)).collect(Collectors.toSet()) : null);
	}

	@JsonIgnore
	public Conclusion getModel() {
		Conclusion model = new Conclusion();
		model.setUuid(getUuid());
		model.setDescription(getDescription());
		return model;
	}

}
