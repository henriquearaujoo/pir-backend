package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Person;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {
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
		setRg(person.getRg());
		setEmitter(person.getEmitter());
		setCpf(person.getCpf());
	}

	@JsonIgnore
	public Person getModel() {
		Person model = new Person();
		model.setCpf(getCpf());
		model.setEmitter(getEmitter());
		model.setRg(getRg());
		return model;
	}
}
