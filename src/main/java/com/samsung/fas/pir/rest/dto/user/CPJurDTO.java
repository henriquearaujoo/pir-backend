package com.samsung.fas.pir.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.models.entity.LegalPerson;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.rest.dto.user.base.CUserBaseDTO;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Pattern;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CPJurDTO extends CUserBaseDTO {
	@ApiObjectField(name="cnpj")
	@Getter
	@JsonProperty("cnpj")
	@CNPJ(message = "user.type.pjur.cnpj.invalid")
	@NotBlank(message="user.type.pjur.cnpj.blank")
	@Pattern(regexp="^(0|[0-9][0-9]*)$", message = "user.pjur.cnpj.invalid.chars")
	private		String			cnpj;
	
	@ApiObjectField(name="ie")
	@Getter
	@JsonProperty("ie")
	@NotBlank(message="user.type.pjur.ie.blank")
	private		String			ie;

	@ApiObjectField(name="social_name")
	@Getter
	@JsonProperty("social_name")
	@NotBlank(message="user.type.pjur.socialname.blank")
	private		String			social;

	@ApiObjectField(name="fantasy_name")
	@Getter
	@JsonProperty("fantasy_name")
	@NotBlank(message="user.type.pjur.fantasyname.blank")
	private		String			fantasy;
	
	private CPJurDTO(LegalPerson model) {
		cnpj					= model.getCnpj();
		ie						= model.getIe();
		social					= model.getSocialName();
		fantasy					= model.getFantasyName();
	}
	
	public CPJurDTO() {
		// JOSN
	}

	@JsonIgnore
	@Override
	public User getModel() {
		LegalPerson 		user 			= new LegalPerson();
		Account 			account			= new Account();

		account.setEnabled(isActive());
		account.setUsername(getLogin());
		account.setPassword(getPassword());

		user.setAddress(getAddressDTO().getModel());
		user.setAccount(account);
		user.setName(getName());
		user.setType(getType());
		user.setEmail(getEmail());
		user.setSocialName(getSocial());
		user.setIe(getIe());
		user.setCnpj(getCnpj());
		user.setFantasyName(getFantasy());

		return user;
	}
	
	public static CPJurDTO toDTO(LegalPerson entity) {
		if (entity != null) {
			return new CPJurDTO(entity);
		}
		return null;
	}
}
