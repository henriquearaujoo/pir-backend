package com.samsung.fas.pir.rest.dto.conclusion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class CConclusionDTO {
	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@NotBlank(message = "chapter.conclusion.description.missing")
	@Getter
	@Setter
	private 	String				description;

	@ApiObjectField(name="chapter", order=2)
	@JsonProperty("chapter")
	@NotBlank(message = "chapter.conclusion.chapter.missing")
	@Getter
	@Setter
	private 	String				chapterID;

	@JsonIgnore
	public Conclusion getModel() {
		Conclusion e = new Conclusion();
		e.setChapter(new Chapter());
		e.setDescription(description);
		e.getChapter().setId(IDCoder.decodeLong(chapterID));
		return e;
	}
}
