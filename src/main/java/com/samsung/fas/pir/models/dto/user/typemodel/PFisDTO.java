package com.samsung.fas.pir.models.dto.user.typemodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.IndividualPerson;
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
	
	private PFisDTO(IndividualPerson embedded) {
		cpf						= embedded.getCpf();
		rg						= embedded.getRg();
		emitter					= embedded.getEmitter();
	}
	
	public PFisDTO() {
		// JSON
	}
	
	@JsonIgnore
	public IndividualPerson getModel() {
		IndividualPerson IndividualPerson = new IndividualPerson();
		IndividualPerson.setCpf(cpf);
		IndividualPerson.setRg(rg);
		IndividualPerson.setEmitter(emitter);
		return IndividualPerson;
	}
	
	public static IndividualPerson toModel(PFisDTO dto) {
		if (dto != null) {
			IndividualPerson IndividualPerson = new IndividualPerson();
			IndividualPerson.setCpf(dto.cpf);
			IndividualPerson.setRg(dto.rg);
			IndividualPerson.setEmitter(dto.emitter);
			return IndividualPerson;
		}
		return null;
	}
	
	public static PFisDTO toDTO(IndividualPerson entity) {
		if (entity != null) {
			return new PFisDTO(entity);
		}
		return null;
	}

}
