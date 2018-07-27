package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.ConservationUnity;
import com.samsung.fas.pir.persistence.models.Regional;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.mobile.device.Device;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DTO(Regional.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConservationUnityDTO extends BaseDTO<ConservationUnity> {
	@Getter
	@Setter
	@JsonProperty("name")
	@NotEmpty(message = "unity.name.empty")
	private		String							name;

	@Getter
	@Setter
	@Length(min = 2, max = 2, message = "abbreviation.two.chars.only")
	@JsonProperty("abbreviation")
	private 	String							abbreviation;

	@Getter
	@Setter
	@JsonProperty(value = "regional", access = JsonProperty.Access.READ_ONLY)
	private 	RegionalDTO						regionalDTO;

	@Getter
	@Setter
	@JsonProperty(value = "communities", access = JsonProperty.Access.READ_ONLY)
	private 	List<CommunityDTO>				communitiesDTO		= new ArrayList<>();

	@Getter
	@Setter
	@Size(min = 1, message = "uc.cities.empty")
	@JsonProperty(value = "cities")
	private 	List<CityDTO>					citiesDTO			= new ArrayList<>();

	public ConservationUnityDTO() {
		super();
	}

	public ConservationUnityDTO(ConservationUnity unity, Device device, boolean detailed) {
		super(unity);
		setCitiesDTO(unity.getCities().stream().map(item -> new CityDTO(item, device, false)).collect(Collectors.toList()));
		setCommunitiesDTO(!device.isNormal()? unity.getCommunities().stream().map(item -> new CommunityDTO(item, device, false)).collect(Collectors.toList()) : null);
		setRegionalDTO(detailed? new RegionalDTO(unity.getRegional(), device, false) : null);
	}

	@JsonIgnore
	@Override
	public ConservationUnity getModel() {
		ConservationUnity model = super.getModel();
		model.setCities(getCitiesDTO().stream().map(CityDTO::getModel).collect(Collectors.toList()));
		return model;
	}
}