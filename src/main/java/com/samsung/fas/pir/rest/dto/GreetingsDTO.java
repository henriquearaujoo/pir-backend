package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Greetings;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GreetingsDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID 		uuid;

	@Getter
	@Setter
	@JsonProperty("chapter_id")
	private 	UUID		chapterUUID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String		description;

	@Getter
	@Setter
	@JsonProperty("turnoff_eletronics")
	private 	boolean		eletronics;

	@Getter
	@Setter
	@JsonProperty("turnoff_stove")
	private 	boolean		stove;

	@Getter
	@Setter
	@JsonProperty("sit_down")
	private 	boolean		sit;

	@Getter
	@Setter
	@JsonProperty("goback_lastvisit")
	private 	boolean		goback;

	public GreetingsDTO() {
		super();
	}

	public GreetingsDTO(Greetings greetings, boolean detailed) {
		setUuid(greetings.getUuid());
		setChapterUUID(greetings.getChapter().getUuid());
		setDescription(greetings.getDescription());
		setEletronics(greetings.isElectronics());
		setSit(greetings.isSit());
		setGoback(greetings.isGoback());
		setStove(greetings.isStove());
	}

	@JsonIgnore
	public Greetings getModel() {
		Greetings e = new Greetings();
		e.setUuid(getUuid());
		e.setDescription(getDescription());
		e.setGoback(isGoback());
		e.setSit(isSit());
		e.setElectronics(isEletronics());
		e.setStove(isStove());
		return e;
	}
}
