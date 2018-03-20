package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Collection;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConclusionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		String						id;

	@Getter
	@Setter
	@JsonProperty("chapter_id")
	private 	String						chapterID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "chapter.greetings.description.missing")
	private 	String						description;

	@Getter
	@Setter
	@JsonProperty("questions")
	private 	Collection<QuestionDTO>		questions;

	public ConclusionDTO() {
		super();
	}

	public ConclusionDTO(Conclusion conclusion, boolean detailed) {
		setId(IDCoder.encode(conclusion.getUuid()));
		setDescription(conclusion.getDescription());
		setChapterID(IDCoder.encode(conclusion.getChapter().getUuid()));
		setQuestions(conclusion.getQuestions() != null? conclusion.getQuestions().stream().map(item -> new QuestionDTO(item, false)).collect(Collectors.toSet()) : null);
	}

	@JsonIgnore
	public Conclusion getModel() {
		Conclusion model = new Conclusion();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setDescription(getDescription());
		return model;
	}

}
