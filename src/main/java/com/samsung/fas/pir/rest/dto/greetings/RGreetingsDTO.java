package com.samsung.fas.pir.rest.dto.greetings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Greetings;
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
	@JsonProperty("description")
	@Getter
	private 	String			description;

	@ApiObjectField(name="chapter", order=2)
	@JsonProperty("chapter")
	@Getter
	private 	String			chapter;

	@ApiObjectField(name="turnoff_eletronics", order=2)
	@JsonProperty("turnoff_eletronics")
	@Getter
	private 	boolean			eletronics;

	@ApiObjectField(name="turnoff_stove", order=2)
	@JsonProperty("turnoff_stove")
	@Getter
	private 	boolean			stove;

	@ApiObjectField(name="sit_down", order=2)
	@JsonProperty("sit_down")
	@Getter
	private 	boolean			sit;

	@ApiObjectField(name="goback_lastvisit", order=2)
	@JsonProperty("goback_lastvisit")
	@Getter
	private 	boolean			goback;

	private RGreetingsDTO(Greetings e) {
		this.id				= IDCoder.encode(e.getId());
		this.description	= e.getDescription();
		this.chapter		= IDCoder.encode(e.getChapter().getId());
		this.eletronics		= e.isEletronics();
		this.sit			= e.isSit();
		this.goback			= e.isGoback();
		this.stove			= e.isStove();
	}

	public static RGreetingsDTO toDTO(Greetings e) {
		if (e != null)
			return new RGreetingsDTO(e);
		return null;
	}
}