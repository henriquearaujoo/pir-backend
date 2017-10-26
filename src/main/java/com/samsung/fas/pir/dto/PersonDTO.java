package com.samsung.fas.pir.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.user.embedded.Person;

import lombok.Getter;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {
	
	@ApiObjectField(name="rg")
	@Getter
	@JsonProperty("rg")
	@NotEmpty(message="user.type.pfis.rg.empty")
	@NotBlank(message="user.type.pfis.rg.blank")
	@Size(min=5, message="user.type.pfis.rg.short")
	private		String			rg;
	
	@ApiObjectField(name="emitter")
	@Getter
	@JsonProperty("emitter")
	@NotEmpty(message="user.type.pfis.rg.emitter.empty")
	@NotBlank(message="user.type.pfis.rg.emitter.blank")
	@Size(min=2, message="user.type.pfis.rg.emitter.short")
	private		String			emitter;
	
	@ApiObjectField(name="cpf")
	@Getter
	@JsonProperty("cpf")
	@NotEmpty(message="user.type.pfis.cpf.empty")
	@NotBlank(message="user.type.pfis.cpf.blank")
	@Size(max=11, min=11, message="user.type.pfis.cpf.size.error")
	private		String			cpf;
	
	private PersonDTO(Person embedded) {
		cpf						= embedded.getCpf();
		rg						= embedded.getRg();
		emitter					= embedded.getEmitter();
	}
	
	public PersonDTO() {
		// JSON
	}
	
	@JsonIgnore
	public Person getModel() {
		Person			person			= new Person();
		person.setCpf(cpf);
		person.setRg(rg);
		person.setEmitter(emitter);
		return person;
	}
	
	public static Person toModel(PersonDTO dto) {
		if (dto != null) {
			Person			person			= new Person();
			person.setCpf(dto.cpf);
			person.setRg(dto.rg);
			person.setEmitter(dto.emitter);
			return person;
		}
		return null;
	}
	
	public static PersonDTO toDTO(Person entity) {
		if (entity != null) {
			return new PersonDTO(entity);
		}
		return null;
	}

}
