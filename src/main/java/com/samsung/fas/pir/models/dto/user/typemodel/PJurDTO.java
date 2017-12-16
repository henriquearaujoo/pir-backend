package com.samsung.fas.pir.models.dto.user.typemodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.LegalPerson;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PJurDTO {
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
	
	private PJurDTO(LegalPerson embedded) {
		cnpj					= embedded.getCnpj();
		ie						= embedded.getIe();
		social					= embedded.getSocialName();
		fantasy					= embedded.getFantasyName();
	}
	
	public PJurDTO() {
		// JOSN
	}
	
	@JsonIgnore
	public LegalPerson getModel() {
		LegalPerson org				= new LegalPerson();
		org.setCnpj(cnpj);
		org.setIe(ie);
		org.setFantasyName(fantasy);
		org.setSocialName(social);
		return org;
	}
	
	public static LegalPerson toModel(PJurDTO dto) {
		if (dto != null) {
			LegalPerson org				= new LegalPerson();
			org.setCnpj(dto.cnpj);
			org.setIe(dto.ie);
			org.setFantasyName(dto.fantasy);
			org.setSocialName(dto.social);
			return org;
		}
		return null;
	}
	
	public static PJurDTO toDTO(LegalPerson entity) {
		if (entity != null) {
			return new PJurDTO(entity);
		}
		return null;
	}
}
