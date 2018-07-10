package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Person;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Pattern;

@DTO(Person.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO extends BaseDTO<Person> {
	@Getter
	@Setter
	@JsonProperty("rg")
	@NotBlank(message="user.rg.missing")
	private		String			rg;

	@Getter
	@Setter
	@JsonProperty("emitter")
	@NotBlank(message="user.emitter.missing")
	private		String			emitter;

	@Getter
	@Setter
	@JsonProperty("cpf")
	@CPF(message = "user.cpf.invalid")
	@Pattern(regexp="^([0-9]*)$", message = "user.cpf.invalid.chars")
	private		String			cpf;

	public PersonDTO() {
		super();
	}

	public PersonDTO(Person person, boolean detailed) {
		super(person);
	}
}
