package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import com.samsung.fas.pir.persistence.models.Community;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Community.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	UUID 			uuid;

	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long			tempID;

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
	@NotBlank(message = "name.missing")
	private 	String			name;

	@Getter
	@Setter
	@JsonProperty("zone")
	@NotBlank(message = "zone.missing")
	private 	String			communityZone;

	@Getter
	@Setter
	@JsonProperty("water_supply")
	@NotBlank(message = "water.supply.missing")
	private 	String			waterSupply;

	@Getter
	@Setter
	@JsonProperty("garbage_destination")
	@NotBlank(message = "garbage.destination.missing")
	private 	String			garbageDestination;

	@Getter
	@Setter
	@JsonProperty("access_via")
	@NotBlank(message = "access.via.missing")
	private 	String			access;

	@Getter
	@Setter
	@JsonProperty("health_service")
	@NotBlank(message = "health.service.missing")
	private 	String			healthServices;

	@Getter
	@Setter
	@JsonProperty("main_income")
	@NotBlank(message = "income.missing")
	private 	String			mainIncome;

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

	@Getter
	@Setter
	@JsonProperty("cultural_production")
	private 	String			culturalProductions;

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
	private 	List<ResponsibleDTO>		responsible;

	@Getter
	@Setter
	@JsonProperty("city_id")
	@NotNull(message = "city.id.missing")
	private 	UUID			cityUUID;


	@ApiModelProperty(hidden = true, readOnly = true)
	@Getter
	@Setter
	@JsonProperty
	private 	CityDTO			city;

	public CommunityDTO() {
		super();
	}

	public CommunityDTO(Community community, Device device, boolean detailed) {
		setTempID(community.getMobileId());
		setUuid(community.getUuid());
		setName(community.getName());
		setWaterSupply(community.getWaterSupply());
		setGarbageDestination(community.getGarbageDestination());
		setAccess(community.getAccess());
		setHealthServices(community.getHealthServices());
		setMainIncome(community.getMainIncome());
		hasKindergarten(community.hasKindergarten());
		hasElementarySchool(community.hasElementarySchool());
		hasHighSchool(community.hasHighSchool());
		hasCollege(community.hasCollege());
		hasElectricity(community.hasElectricity());
		hasCommunityCenter(community.hasCommunityCenter());
		hasReligiousPlace(community.hasReligiousPlace());
		hasCulturalEvents(community.hasCulturalEvents());
		hasPatron(community.hasPatron());
		setCommunityZone(community.getCommunityZone() != null? community.getCommunityZone().getValue() : ECommunityZone.UNDEFINED.getValue());
		hasCommunityLeaders(community.hasCommunityLeaders());
		setCulturalProductions(community.getCulturalProductions());
		setRegional(community.getRegional());
		setUc(community.getUc());
		setLatitude(community.getLatitude());
		setLongitude(community.getLongitude());
		setCity(community.getCity() != null? new CityDTO(community.getCity(), false) : null);
		setResponsible(/*!device.isNormal()? */community.getResponsible() != null? community.getResponsible().stream().map(item -> new ResponsibleDTO(item, device, false)).collect(Collectors.toList()) : new ArrayList<>());
	}

	@JsonIgnore
	public Community getModel() {
		Community model = new Community();
		model.setMobileId(getTempID());
		model.setUuid(getUuid());
		model.setName(getName());
		model.setWaterSupply(getWaterSupply());
		model.setGarbageDestination(getGarbageDestination());
		model.setAccess(getAccess());
		model.setHealthServices(getHealthServices());
		model.setMainIncome(getMainIncome());
		model.hasKindergarten(hasKindergarten());
		model.hasElementarySchool(hasElementarySchool());
		model.hasHighSchool(hasHighSchool());
		model.hasCollege(hasCollege());
		model.hasElectricity(hasElectricity());
		model.hasCommunityCenter(hasCommunityCenter());
		model.hasReligiousPlace(hasReligiousPlace());
		model.hasCulturalEvents(hasCulturalEvents());
		model.hasPatron(hasPatron());
		model.setCulturalProductions(getCulturalProductions());
		model.hasCommunityLeaders(hasCommunityLeaders());
		model.setRegional(getRegional());
		model.setUc(getUc());
		model.setCommunityZone(ECommunityZone.setValue(getCommunityZone()));
		model.setLatitude(getLatitude());
		model.setLongitude(getLongitude());
		model.setResponsible(getResponsible() != null? getResponsible().stream().map(ResponsibleDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}
