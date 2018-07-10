package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.mobile.device.Device;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Community.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityDTO extends BaseDTO<Community> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long			mobileId;

	@Getter
	@Setter
	@JsonProperty("uc")
	private 	String			uc;

	@Getter
	@Setter
	@JsonProperty("regional")
	private 	String			regional;

	@Getter
	@Setter
	@JsonProperty("name")
	private 	String			name;

	@Getter
	@Setter
	@JsonProperty("zone")
	private 	String			communityZone;

	@Getter
	@Setter
	@JsonProperty("water_supply")
	private 	String			waterSupply;

	@Getter
	@Setter
	@JsonProperty("garbage_destination")
	private 	String			garbageDestination;

	@Getter
	@Setter
	@JsonProperty("access_via")
	private 	String			access;

	@Getter
	@Setter
	@JsonProperty("health_service")
	private 	String			healthServices;

	@Getter
	@Setter
	@JsonProperty("main_income")
	private 	String			mainIncome;

	@Getter
	@Setter
	@JsonProperty("cultural_production")
	private 	String			culturalProductions;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_kindergarten")
	private 	boolean			hasKindergarten;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_elementary_school")
	private 	boolean			hasElementarySchool;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_high_school")
	private 	boolean			hasHighSchool;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_college")
	private 	boolean			hasCollege;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_eletricity")
	private 	boolean			hasElectricity;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_community_center")
	private 	boolean			hasCommunityCenter;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_religious_place")
	private 	boolean			hasReligiousPlace;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_cultural_events")
	private 	boolean			hasCulturalEvents;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_patron")
	private 	boolean			hasPatron;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_community_leaders")
	private 	boolean			hasCommunityLeaders;

	@Getter
	@Setter
	@JsonProperty("latitude")
	private 	Double			latitude;

	@Getter
	@Setter
	@JsonProperty("longitude")
	private 	Double			longitude;

	@Getter
	@Setter
	@JsonProperty("responsible")
	private 	List<ResponsibleDTO>		responsibleDTO;

	@Getter
	@Setter
	@JsonProperty("city_id")
	@NotNull(message = "city.id.missing")
	private 	UUID			cityUUID;


	@ApiModelProperty(hidden = true, readOnly = true)
	@Getter
	@Setter
	@JsonProperty("city")
	private 	CityDTO			cityDTO;

	public CommunityDTO() {
		super();
	}

	public CommunityDTO(Community community, Device device, boolean detailed) {
		super(community);
		setCommunityZone(community.getCommunityZone() != null? community.getCommunityZone().getValue() : ECommunityZone.UNDEFINED.getValue());
		setCityDTO(community.getCity() != null? new CityDTO(community.getCity(), device, false) : null);
		setResponsibleDTO(device != null && !device.isNormal()? community.getResponsible() != null? community.getResponsible().stream().map(item -> new ResponsibleDTO(item, device, false)).collect(Collectors.toList()) : new ArrayList<>() : null);
	}

	@JsonIgnore
	@Override
	public Community getModel() {
		Community model = super.getModel();
		model.setCommunityZone(ECommunityZone.setValue(getCommunityZone()));
		model.setResponsible(getResponsibleDTO() != null? getResponsibleDTO().stream().map(ResponsibleDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}
