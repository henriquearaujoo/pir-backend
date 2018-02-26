package com.samsung.fas.pir.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.models.entity.IndividualPerson;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.rest.dto.user.base.UUserBaseDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;

public class UPFisDTO extends UUserBaseDTO {
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

	private UPFisDTO(IndividualPerson model) {
		cpf						= model.getCpf();
		rg						= model.getRg();
		emitter					= model.getEmitter();
	}

	public UPFisDTO() {
		// JSON
	}

	@JsonIgnore
	@Override
	public User getModel() {
		User 				user 		= new User();
		IndividualPerson	individual	= new IndividualPerson();
		Account 			account		= new Account();

		account.setEnabled(isActive());
		account.setUsername(getLogin());
		account.setPassword(getPassword());

		individual.setRg(getRg());
		individual.setCpf(getCpf());
		individual.setEmitter(getEmitter());

		user.setUuid(IDCoder.decode(getId()));
		user.setAddress(getAddressDTO().getModel());
		user.setAccount(account);
		user.setName(getName());
		user.setType(getType());
		user.setEmail(getEmail());
		user.setIndividual(individual);

		return user;
	}

	public static UPFisDTO toDTO(IndividualPerson entity) {
		if (entity != null) {
			return new UPFisDTO(entity);
		}
		return null;
	}
}
