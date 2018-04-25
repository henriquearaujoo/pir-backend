package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.LegalEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.Pattern;

public class EntityDTO {
	@Getter
	@Setter
	@JsonProperty("cnpj")
	@CNPJ(message = "user.cnpj.invalid")
	@Pattern(regexp="^([0-9]*)$", message = "user.cnpj.invalid.chars")
	private		String			cnpj;

	@Getter
	@Setter
	@JsonProperty("ie")
	@NotBlank(message="user.ie.missing")
	private		String			ie;

	@Getter
	@Setter
	@JsonProperty("social_name")
	@NotBlank(message="user.socialname.missing")
	private		String			social;

	@Getter
	@Setter
	@JsonProperty("fantasy_name")
	@NotBlank(message="user.fantasyname.missing")
	private		String			fantasy;

	public EntityDTO() {
		super();
	}

	public EntityDTO(LegalEntity entity, boolean detailed) {
		setCnpj(entity.getCnpj());
		setIe(entity.getIe());
		setSocial(entity.getSocialName());
		setFantasy(entity.getFantasyName());
	}

	@JsonIgnore
	public LegalEntity getModel() {
		LegalEntity model = new LegalEntity();
		model.setCnpj(getCnpj());
		model.setFantasyName(getFantasy());
		model.setIe(getIe());
		model.setSocialName(getSocial());
		return model;
	}
}
