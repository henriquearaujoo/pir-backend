package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.persistence.models.enums.ECommunityZone;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String			id;

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
	private 	boolean			hasEletricity;

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
	@JsonProperty("city_id")
	@NotNull(message = "city.id.missing")
	private 	String			cityId;

	@Getter
	@Setter
	@JsonProperty
	private 	CityDTO			city;

	public CommunityDTO() {
		super();
	}

	public CommunityDTO(Community community, boolean detailed) {
		setId(IDCoder.encode(community.getUuid()));
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
		hasEletricity(community.hasEletricity());
		hasCommunityCenter(community.hasCommunityCenter());
		hasReligiousPlace(community.hasReligiousPlace());
		hasCulturalEvents(community.hasCulturalEvents());
		hasPatron(community.hasPatron());
		setCommunityZone(community.getCommunityZone().toString());
		hasCommunityLeaders(community.hasCommunityLeaders());
		setCity(new CityDTO(community.getCity(), false));
		setCulturalProductions(community.getCulturalProductions());
		setCommunityZone(community.getCommunityZone().toString());
		setRegional(community.getRegional());
		setUc(community.getUc());
	}

	@JsonIgnore
	public Community getModel() {
		Community model = new Community();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
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
		model.hasEletricity(hasEletricity());
		model.hasCommunityCenter(hasCommunityCenter());
		model.hasReligiousPlace(hasReligiousPlace());
		model.hasCulturalEvents(hasCulturalEvents());
		model.hasPatron(hasPatron());
		model.setCulturalProductions(getCulturalProductions());
		model.hasCommunityLeaders(hasCommunityLeaders());
		model.setRegional(getRegional());
		model.setUc(getUc());
		model.setCommunityZone(ECommunityZone.setValue(getCommunityZone()));
		return model;
	}
}
