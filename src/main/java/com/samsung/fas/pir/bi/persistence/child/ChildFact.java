package com.samsung.fas.pir.bi.persistence.child;

import com.samsung.fas.pir.bi.persistence.base.BIFactBase;
import com.samsung.fas.pir.bi.persistence.base.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.base.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.base.common.GenderDimension;
import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.community.*;
import com.samsung.fas.pir.bi.persistence.family.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse")
public class ChildFact extends BIFactBase {
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_date_dimension"))
	private			DateDimension								date;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_agent_dimension"))
	private 		AgentDimension 								agent;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_name_dimension"))
	private 		NameDimension 								name;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_gender_dimension"))
	private			GenderDimension								gender;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_mother_dimension"))
	private			NameDimension								motherName;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_father_dimension"))
	private			NameDimension								fatherName;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_data_dimension"))
	private			FamilyDataDimension							data;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_family_name_dimension"))
	private			NameDimension								familyName;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_income_dimension"))
	private			FamilyIncomeDimension						income;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_water_treatment_dimension"))
	private			FamilyWaterTreatmentDimension				waterTreatment;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_access_dimension"))
	private				CommunityAccessDimension				communityAccess;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_community_data_dimension"))
	private				CommunityDataDimension					communityData;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_cultural_production_dimension"))
	private				CommunityCulturalProductionDimension	culturalProduction;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_garbage_destination_dimension"))
	private				CommunityGarbageDestinationDimension	hasGarbageDestination;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_health_service_dimension"))
	private				CommunityHealthServiceDimension			hasHealthService;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_community_income_dimension"))
	private				CommunityIncomeDimension				communityIncome;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_water_supply_dimension"))
	private				CommunityWaterSupplyDimension			waterSupply;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_regional_dimension"))
	private				CommunityRegionalDimension				regional;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_unity_dimension"))
	private				CommunityUnityDimension					unity;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_city_dimension"))
	private				CommunityCityDimension					city;
}