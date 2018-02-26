package com.samsung.fas.pir.rest.dto.greetings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Greetings;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RGreetingsDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="description", order=1)
	@JsonProperty("description")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="chapter", order=2)
	@JsonProperty("chapter")
	@Getter
	@Setter
	private 	String			chapter;

	@ApiObjectField(name="turnoff_eletronics", order=2)
	@JsonProperty("turnoff_eletronics")
	@Getter
	@Setter
	private 	boolean			eletronics;

	@ApiObjectField(name="turnoff_stove", order=2)
	@JsonProperty("turnoff_stove")
	@Getter
	@Setter
	private 	boolean			stove;

	@ApiObjectField(name="sit_down", order=2)
	@JsonProperty("sit_down")
	@Getter
	@Setter
	private 	boolean			sit;

	@ApiObjectField(name="goback_lastvisit", order=2)
	@JsonProperty("goback_lastvisit")
	@Getter
	@Setter
	private 	boolean			goback;

	private RGreetingsDTO(Greetings e) {
		setId(IDCoder.encode(e.getUuid()));
		setChapter(IDCoder.encode(e.getChapter().getUuid()));
		setDescription(e.getDescription());
		setEletronics(e.isEletronics());
		setSit(e.isSit());
		setGoback(e.isGoback());
		setStove(e.isStove());
	}

	public static RGreetingsDTO toDTO(Greetings e) {
		if (e != null)
			return new RGreetingsDTO(e);
		return null;
	}
}