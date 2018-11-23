package com.samsung.fas.pir.bi.persistence.family;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_family_service", columnList = "inSocialProgram, hasNearbyBasicUnity, hasSanitation, hasWaterTreatment, waterTreatment", unique = true))
public class FamilyServiceDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				boolean						inSocialProgram;

	@Getter
	@Setter
	@Column
	private				boolean						hasNearbyBasicUnity;

	@Getter
	@Setter
	@Column
	private				boolean						hasSanitation;

	@Getter
	@Setter
	@Column
	private				boolean						hasWaterTreatment;

	@Getter
	@Setter
	@Column
	private				String						waterTreatment;
}