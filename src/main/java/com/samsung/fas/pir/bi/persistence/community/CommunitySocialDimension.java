package com.samsung.fas.pir.bi.persistence.community;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_community_social", columnList = "culturalProduction, income, hasCivicCenter, hasCulturalEvent, hasLeader, hasPatron, hasReligiousPlace", unique = true))
public class CommunitySocialDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String				culturalProduction;

	@Getter
	@Setter
	@Column
	private				String				income;

	@Getter
	@Setter
	@Column
	private				boolean				hasCivicCenter;

	@Getter
	@Setter
	@Column
	private				boolean				hasCulturalEvent;

	@Getter
	@Setter
	@Column
	private				boolean				hasLeader;

	@Getter
	@Setter
	@Column
	private				boolean				hasPatron;

	@Getter
	@Setter
	@Column
	private				boolean				hasReligiousPlace;
}
