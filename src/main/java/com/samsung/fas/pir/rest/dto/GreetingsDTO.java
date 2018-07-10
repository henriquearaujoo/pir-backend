package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Greetings;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import java.util.UUID;

@DTO(Greetings.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GreetingsDTO extends BaseDTO<Greetings> {
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
	private 	boolean		electronics;

	@Getter
	@Setter
	@JsonProperty("sit_down")
	private 	boolean		sit;

	@Getter
	@Setter
	@JsonProperty("goback_lastvisit")
	private 	boolean		goback;

	@Getter
	@Setter
	@JsonProperty("turnoff_stove")
	private 	boolean		stove;

	public GreetingsDTO() {
		super();
	}

	public GreetingsDTO(Greetings greetings, Device device, boolean detailed) {
		super(greetings);
		setChapterUUID(greetings.getChapter().getUuid());
	}
}
