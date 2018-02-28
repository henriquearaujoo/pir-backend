package com.samsung.fas.pir.rest.dto.greetings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Greetings;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CRUGreetingsDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		String			id;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String			description;

	@Getter
	@Setter
	@JsonProperty("chapter")
	private 	String			chapterID;

	@Getter
	@Setter
	@JsonProperty("turnoff_eletronics")
	private 	boolean			eletronics;

	@Getter
	@Setter
	@JsonProperty("turnoff_stove")
	private 	boolean			stove;

	@Getter
	@Setter
	@JsonProperty("sit_down")
	private 	boolean			sit;

	@Getter
	@Setter
	@JsonProperty("goback_lastvisit")
	private 	boolean			goback;

	public CRUGreetingsDTO() {
		super();
	}

	public CRUGreetingsDTO(Greetings greetings) {
		setId(IDCoder.encode(greetings.getUuid()));
		setChapterID(IDCoder.encode(greetings.getChapter().getUuid()));
		setDescription(greetings.getDescription());
		setEletronics(greetings.isEletronics());
		setSit(greetings.isSit());
		setGoback(greetings.isGoback());
		setStove(greetings.isStove());
	}

	@JsonIgnore
	public Greetings getModel() {
		Greetings e = new Greetings();
		e.setUuid(IDCoder.decode(getId()));
		e.setDescription(getDescription());
		e.setGoback(isGoback());
		e.setSit(isSit());
		e.setEletronics(isEletronics());
		e.setStove(isStove());
		return e;
	}
}
