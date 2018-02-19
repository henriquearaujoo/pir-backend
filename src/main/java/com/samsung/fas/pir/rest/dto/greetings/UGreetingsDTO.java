package com.samsung.fas.pir.rest.dto.greetings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Greetings;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
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
	private 	String			chapterID;

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

	@JsonIgnore
	public Greetings getModel() {
		Greetings e = new Greetings();
		e.setChapter(new Chapter());
		e.setUuid(IDCoder.decode(getId()));
		e.setDescription(getDescription());
		e.setGoback(isGoback());
		e.setSit(isSit());
		e.setEletronics(isEletronics());
		e.setStove(isStove());
		e.getChapter().setUuid(IDCoder.decode(chapterID));
		return e;
	}
}
