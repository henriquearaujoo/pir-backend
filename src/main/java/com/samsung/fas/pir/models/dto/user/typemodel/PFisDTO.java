package com.samsung.fas.pir.models.dto.user.typemodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.user.embedded.Person;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PFisDTO {
	
	@ApiObjectField(name="rg")
	@Getter
	@JsonProperty("rg")
	@NotBlank(message="user.type.pfis.rg.blank")
	@Size(min=5, message="user.type.pfis.rg.short")
	private		String			rg;
	
	@ApiObjectField(name="emitter")
	@Getter
	@JsonProperty("emitter")
	@NotBlank(message="user.type.pfis.rg.emitter.blank")
	@Size(min=2, message="user.type.pfis.rg.emitter.short")
	private		String			emitter;
	
	@ApiObjectField(name="cpf")
	@Getter
	@JsonProperty("cpf")
	@CPF(message = "user.type.pfis.cpf.invalid")
	@NotBlank(message="user.type.pfis.cpf.blank")
	private		String			cpf;
	
	private PFisDTO(Person embedded) {
		cpf						= embedded.getCpf();
		rg						= embedded.getRg();
		emitter					= embedded.getEmitter();
	}
	
	public PFisDTO() {
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
	
	public static Person toModel(PFisDTO dto) {
		if (dto != null) {
			Person			person			= new Person();
			person.setCpf(dto.cpf);
			person.setRg(dto.rg);
			person.setEmitter(dto.emitter);
			return person;
		}
		return null;
	}
	
	public static PFisDTO toDTO(Person entity) {
		if (entity != null) {
			return new PFisDTO(entity);
		}
		return null;
	}

}
