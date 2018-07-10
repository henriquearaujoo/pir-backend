package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.LegalEntity;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.mobile.device.Device;

import javax.validation.constraints.Pattern;

@DTO(LegalEntity.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityDTO extends BaseDTO<LegalEntity> {
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
	private		String			socialName;

	@Getter
	@Setter
	@JsonProperty("fantasy_name")
	@NotBlank(message="user.fantasyname.missing")
	private		String			fantasyName;

	public EntityDTO() {
		super();
	}

	public EntityDTO(LegalEntity entity, Device device, boolean detailed) {
		super(entity);
	}
}