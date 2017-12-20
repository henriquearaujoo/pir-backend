package com.samsung.fas.pir.models.dto.conclusion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.question.UQuestionDTO;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.models.entity.Conclusion;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class UConclusionDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@NotBlank(message = "chapter.greetings.id.missing")
	@Getter
	@Setter
	private		String				id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@NotBlank(message = "chapter.greetings.description.missing")
	@Getter
	@Setter
	private 	String				description;

	@ApiObjectField(name="chapter", order=2)
	@JsonProperty("chapter")
	@Getter
	@Setter
	private 	String				chapterID;

	@ApiObjectField(name="questions", order=2)
	@JsonProperty("questions")
	@NotNull(message = "chapter.conclusion.questions.missing")
	@Valid
	@Getter
	@Setter
	private 	Set<UQuestionDTO>	questions;

	@JsonIgnore
	public Conclusion getModel() {
		Conclusion e = new Conclusion();
		e.setChapter(new Chapter());
		e.setDescription(description);
		e.getChapter().setId(IDCoder.decodeLong(chapterID));
		e.setId(IDCoder.decodeLong(id));
		e.setQuestions(questions.stream().map(UQuestionDTO::getModel).collect(Collectors.toCollection(HashSet::new)));
		e.getQuestions().forEach(item -> item.setConclusion(e));
		return e;
	}

}
