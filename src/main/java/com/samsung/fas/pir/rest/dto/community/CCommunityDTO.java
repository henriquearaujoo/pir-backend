package com.samsung.fas.pir.rest.dto.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.persistence.models.enums.ECommunityZone;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;

@ApiObject(visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA, show = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CCommunityDTO {
	@Getter
	@Setter
	@JsonProperty("name")
	@NotEmpty(message = "name.missing")
	private 	String			name;

	@Getter
	@Setter
	@JsonProperty("zone")
	@NotEmpty(message = "zone.missing")
	private 	String			communityZone;

	@Getter
	@Setter
	@JsonProperty("water_supply")
	@NotEmpty(message = "water.supply.missing")
	private 	String			waterSupply;

	@Getter
	@Setter
	@JsonProperty("garbage_destination")
	@NotEmpty(message = "garbage.destination.missing")
	private 	String			garbageDestination;

	@Getter
	@Setter
	@JsonProperty("access_via")
	@NotEmpty(message = "access.via.missing")
	private 	String			access;

	@Getter
	@Setter
	@JsonProperty("health_service")
	@NotEmpty(message = "health.service.missing")
	private 	String			healthServices;

	@Getter
	@Setter
	@JsonProperty("main_income")
	@NotEmpty(message = "income.missing")
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

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_cultural_production")
	private 	boolean			hasCulturalProductions;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_community_leaders")
	private 	boolean			hasCommunityLeaders;

	@Getter
	@Setter
	@JsonProperty("city_id")
	@NotEmpty(message = "city.id.missing")
	private 	Long			city;

	@JsonIgnore
	public Community getModel() {
		Community model = new Community();
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
		model.hasCulturalProductions(hasCulturalProductions());
		model.hasCommunityLeaders(hasCommunityLeaders());
		model.setCommunityZone(ECommunityZone.parse(getCommunityZone()));
		return model;
	}
}
