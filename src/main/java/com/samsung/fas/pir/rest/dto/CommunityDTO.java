package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import io.swagger.annotations.Api;
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
	private		long					externalID;

	@Getter
	@Setter
	@JsonProperty("name")
	private 	String					name;

	@Getter
	@Setter
	@JsonProperty("zone")
	private 	ECommunityZone			communityZone;

	@Getter
	@Setter
	@JsonProperty("water_supply")
	private 	String					waterSupply;

	@Getter
	@Setter
	@JsonProperty("garbage_destination")
	private 	String					garbageDestination;

	@Getter
	@Setter
	@JsonProperty("access_via")
	private 	String					access;

	@Getter
	@Setter
	@JsonProperty("health_service")
	private 	String					healthServices;

	@Getter
	@Setter
	@JsonProperty("main_income")
	private 	String					mainIncome;

	@Getter
	@Setter
	@JsonProperty("cultural_production")
	private 	String					culturalProductions;

	@Getter
	@Setter
	@JsonProperty("has_kindergarten")
	private 	boolean					kindergarten;

	@Getter
	@Setter
	@JsonProperty("has_elementary_school")
	private 	boolean					elementarySchool;

	@Getter
	@Setter
	@JsonProperty("has_high_school")
	private 	boolean					highSchool;

	@Getter
	@Setter
	@JsonProperty("has_college")
	private 	boolean					college;

	@Getter
	@Setter
	@JsonProperty("has_eletricity")
	private 	boolean					electricity;

	@Getter
	@Setter
	@JsonProperty("has_community_center")
	private 	boolean					communityCenter;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@JsonProperty("has_religious_place")
	private 	boolean					religiousPlace;

	@Getter
	@Setter
	@JsonProperty("has_cultural_events")
	private 	boolean					culturalEvents;

	@Getter
	@Setter
	@JsonProperty("has_patron")
	private 	boolean					patron;

	@Getter
	@Setter
	@JsonProperty("has_community_leaders")
	private 	boolean					communityLeaders;

	@Getter
	@Setter
	@JsonProperty("latitude")
	private 	Double					latitude;

	@Getter
	@Setter
	@JsonProperty("longitude")
	private 	Double					longitude;

	// region Relations
	@Getter
	@Setter
	@JsonProperty("family")
	private 	List<FamilyDTO>			familyDTO;

	@Getter
	@Setter
	@JsonProperty(value = "unity")
	@NotNull(message = "unity.missing")
	private 	ConservationUnityDTO	unityDTO;

	@Getter
	@Setter
	@JsonProperty(value = "city")
	@NotNull(message = "city.missing")
	private 	CityDTO					cityDTO;
	// endregion

	public CommunityDTO() {
		super();
	}

	public CommunityDTO(Community community, Device device, boolean detailed) {
		super(community);
		setCityDTO(community.getCity() != null? new CityDTO(community.getCity(), device, false) : null);
		setUnityDTO(community.getUnity() != null? new ConservationUnityDTO(community.getUnity(), device, false) : null);
		setFamilyDTO(!device.isNormal()? community.getFamily().stream().map(item -> new FamilyDTO(item, device, false)).collect(Collectors.toList()) : null);
		if (getUnityDTO() != null) {
			getUnityDTO().setCitiesDTO(null);
			getUnityDTO().setRegionalDTO(new RegionalDTO(community.getUnity().getRegional(), device, false));
			getUnityDTO().getRegionalDTO().setUnitiesDTO(null);
		}
	}

	@JsonIgnore
	@Override
	public Community getModel() {
		Community model = super.getModel();
		model.setUnity(getUnityDTO().getModel());
		model.setCity(getCityDTO().getModel());
		model.setFamily(this.getFamilyDTO() != null? this.getFamilyDTO().stream().map(FamilyDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}
