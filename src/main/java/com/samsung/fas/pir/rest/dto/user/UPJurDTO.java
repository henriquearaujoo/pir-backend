package com.samsung.fas.pir.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.models.entity.LegalPerson;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.rest.dto.user.base.UUserBaseDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.jsondoc.core.annotation.ApiObjectField;

public class UPJurDTO extends UUserBaseDTO {
	@ApiObjectField(name="cnpj")
	@Getter
	@JsonProperty("cnpj")
	@CNPJ(message = "user.type.pjur.cnpj.invalid")
	@NotBlank(message="user.type.pjur.cnpj.blank")
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

	private UPJurDTO(LegalPerson model) {
		cnpj					= model.getCnpj();
		ie						= model.getIe();
		social					= model.getSocialName();
		fantasy					= model.getFantasyName();
	}

	public UPJurDTO() {
		// JOSN
	}

	@JsonIgnore
	@Override
	public User getModel() {
		User 		user 		= new User();
		LegalPerson	legal		= new LegalPerson();
		Account 	account		= new Account();

		account.setEnabled(isActive());
		account.setUsername(getLogin());
		account.setPassword(getPassword());

		legal.setSocialName(getSocial());
		legal.setIe(getIe());
		legal.setCnpj(getCnpj());
		legal.setFantasyName(getFantasy());

		user.setUuid(IDCoder.decode(getId()));
		user.setAddress(getAddressDTO().getModel());
		user.setAccount(account);
		user.setName(getName());
		user.setType(getType());
		user.setEmail(getEmail());
		user.setLegal(legal);

		return user;
	}

	public static UPJurDTO toDTO(LegalPerson entity) {
		if (entity != null) {
			return new UPJurDTO(entity);
		}
		return null;
	}
}
