package com.samsung.fas.pir.models.dto.greetings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Greetings;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObjectField;

public class UGreetingsDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@NotBlank(message = "chapter.greetings.id.missing")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@NotBlank(message = "chapter.greetings.description.missing")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="chapter", order=2)
	@JsonProperty("chapter")
	@NotBlank(message = "chapter.greetings.chapter.missing")
	@Getter
	@Setter
	private 	String			chapter;

	@JsonIgnore
	public Greetings getModel() {
		Greetings e = new Greetings();
		e.setId(IDCoder.decodeLong(id));
		e.setDescription(description);
		return e;
	}
}
