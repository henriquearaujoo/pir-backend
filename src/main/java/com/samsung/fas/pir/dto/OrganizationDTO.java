package com.samsung.fas.pir.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.user.embedded.Organization;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationDTO {
	
	@Getter
	@JsonProperty("cnpj")
	@NotEmpty(message="user.type.pjur.cnpj.empty")
	@NotBlank(message="user.type.pjur.cnpj.blank")
	@Size(min=14, max=14, message="user.type.pjur.cnpj.size.error")
	private		String			cnpj;
	
	@Getter
	@JsonProperty("ie")
	@NotEmpty(message="user.type.pjur.ie.empty")
	@NotBlank(message="user.type.pjur.ie.blank")
	private		String			ie;
	
	private OrganizationDTO(Organization embedded) {
		cnpj					= embedded.getCnpj();
		ie						= embedded.getIe();
	}
	
	public OrganizationDTO() {
		// JOSN
	}
	
	@JsonIgnore
	public Organization getModel() {
		Organization	org				= new Organization();
		org.setCnpj(cnpj);
		org.setIe(ie);
		return org;
	}
	
	public static Organization toModel(OrganizationDTO dto) {
		if (dto != null) {
			Organization	org				= new Organization();
			org.setCnpj(dto.cnpj);
			org.setIe(dto.ie);
			return org;
		}
		return null;
	}
	
	public static OrganizationDTO toDTO(Organization entity) {
		if (entity != null) {
			return new OrganizationDTO(entity);
		}
		return null;
	}
}
