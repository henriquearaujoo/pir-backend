package com.samsung.fas.pir.bi.persistence.community;

import com.samsung.fas.pir.bi.persistence.base.BIFactBase;
import com.samsung.fas.pir.bi.persistence.base.common.DateDimension;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse")
public class CommunityFact extends BIFactBase {
	// region Dimensions
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_date_dimension"))
	private				DateDimension							dateDimension;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_access_dimension"))
	private				CommunityAccessDimension				communityAccess;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_data_dimension"))
	private				CommunityDataDimension					data;

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
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_income_dimension"))
	private				CommunityIncomeDimension				income;

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
	// endregion
}
