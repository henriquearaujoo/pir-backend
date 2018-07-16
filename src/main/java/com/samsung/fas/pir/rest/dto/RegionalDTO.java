package com.samsung.fas.pir.rest.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Regional;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.mobile.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@DTO(Regional.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionalDTO extends BaseDTO<Regional> {
	@Getter
	@Setter
	@JsonProperty("name")
	@NotEmpty(message = "unity.name.empty")
	private		String							name;

	@Getter
	@Setter
	@JsonProperty("unities")
	private 	List<ConservationUnityDTO>		unitiesDTO			= new ArrayList<>();

	public RegionalDTO() {
		super();
	}

	public RegionalDTO(Regional regional, Device device, boolean detailed) {
		super(regional);
		setUnitiesDTO(regional != null? regional.getUnities().stream().map(item -> new ConservationUnityDTO(item, device, false)).collect(Collectors.toList()) : null);
	}

	@JsonIgnore
	public Regional getModel() {
		Regional model = super.getModel();
		model.setName(getName());
		model.setUnities(unitiesDTO.stream().map(ConservationUnityDTO::getModel).collect(Collectors.toList()));
		return model;
	}
}