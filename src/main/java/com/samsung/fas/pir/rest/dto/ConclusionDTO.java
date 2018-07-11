package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Conclusion.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConclusionDTO extends BaseDTO<Conclusion> {
	@Getter
	@Setter
	@JsonProperty("chapter_id")
	private 	UUID 						chapterUUID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "chapter.greetings.description.missing")
	private 	String						description;

	@Getter
	@Setter
	@JsonProperty("observations")
	private 	String 						observations;

	@Getter
	@Setter
	@JsonProperty("questions")
	private 	Collection<QuestionDTO>		questionsDTO;

	public ConclusionDTO() {
		super();
	}

	public ConclusionDTO(Conclusion conclusion, boolean detailed) {
		super(conclusion);
		setChapterUUID(conclusion.getChapter().getUuid());
		setQuestionsDTO(conclusion.getQuestions() != null? conclusion.getQuestions().stream().sorted(Comparator.comparing(BaseID::getId)).map(item -> new QuestionDTO(item, false)).collect(Collectors.toList()) : null);
	}
}
