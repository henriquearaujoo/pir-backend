package com.samsung.fas.pir.rest.dto.community;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class RCommunityDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String			id;

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

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_community_leaders")
	private 	boolean			hasCommunityLeaders;

	@Getter
	@Setter
	@JsonProperty("city_id")
	private 	long			city;

	public RCommunityDTO() {
		super();
	}

	public RCommunityDTO(Community entity) {
		setId(IDCoder.encode(entity.getUuid()));
		setName(entity.getName());
		setWaterSupply(entity.getWaterSupply());
		setGarbageDestination(entity.getGarbageDestination());
		setAccess(entity.getAccess());
		setHealthServices(entity.getHealthServices());
		setMainIncome(entity.getMainIncome());
		hasKindergarten(entity.hasKindergarten());
		hasElementarySchool(entity.hasElementarySchool());
		hasHighSchool(entity.hasHighSchool());
		hasCollege(entity.hasCollege());
		hasEletricity(entity.hasEletricity());
		hasCommunityCenter(entity.hasCommunityCenter());
		hasReligiousPlace(entity.hasReligiousPlace());
		hasCulturalEvents(entity.hasCulturalEvents());
		hasPatron(entity.hasPatron());
		setCommunityZone(entity.getCommunityZone().toString());
		hasCommunityLeaders(entity.hasCommunityLeaders());
		setCity(entity.getCity().getId());
		setCulturalProductions(entity.getCulturalProductions());
		setCommunityZone(entity.getCommunityZone().toString());
	}
}
