package com.samsung.fas.pir.models.dto.greetings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Greetings;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RGreetingsDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	private		String			id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("number")
	@Getter
	private 	String			description;

	@ApiObjectField(name="chapter", order=2)
	@JsonProperty("chapter")
	@Getter
	private 	String			chapter;

	private RGreetingsDTO(Greetings e) {
		this.id				= IDCoder.encode(e.getId());
		this.description	= e.getDescription();
		this.chapter		= IDCoder.encode(e.getChapter().getId());
	}

	public static RGreetingsDTO toDTO(Greetings e) {
		if (e != null)
			return new RGreetingsDTO(e);
		return null;
	}
}