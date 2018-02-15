package com.samsung.fas.pir.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.models.entity.IndividualPerson;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.rest.dto.user.base.CUserBaseDTO;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CPFisDTO extends CUserBaseDTO {
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
	@Pattern(regexp="^(0|[0-9][0-9]*)$", message = "user.pfis.cpf.invalid.chars")
	private		String			cpf;
	
	private CPFisDTO(IndividualPerson model) {
		cpf						= model.getCpf();
		rg						= model.getRg();
		emitter					= model.getEmitter();
	}
	
	public CPFisDTO() {
		// JSON
	}

	@JsonIgnore
	@Override
	public User getModel() {
		IndividualPerson 	user 			= new IndividualPerson();
		Account				account			= new Account();

		account.setEnabled(isActive());
		account.setUsername(getLogin());
		account.setPassword(getPassword());

		user.setAddress(getAddressDTO().getModel());
		user.setAccount(account);
		user.setName(getName());
		user.setType(getType());
		user.setEmail(getEmail());
		user.setRg(getRg());
		user.setCpf(getCpf());
		user.setEmitter(getEmitter());

		return user;
	}
	
	public static CPFisDTO toDTO(IndividualPerson entity) {
		if (entity != null) {
			return new CPFisDTO(entity);
		}
		return null;
	}

}
