package com.samsung.fas.pir.bi.persistence.community;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_community_data", columnList = "zone, hasElectricity, hasCivicCenter, hasCulturalEvent, hasLeader, hasPatron, hasReligiousPlace, hasKindergarten, hasElementarySchool, hasHighSchool, hasCollege", unique = true))
public class CommunityDataDimension extends BIBase {
	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	private 			ECommunityZone		zone;

	@Getter
	@Setter
	@Column
	private				boolean				hasElectricity;

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

	@Getter
	@Setter
	@Column
	private				boolean				hasKindergarten;

	@Getter
	@Setter
	@Column
	private				boolean				hasElementarySchool;

	@Getter
	@Setter
	@Column
	private				boolean				hasHighSchool;

	@Getter
	@Setter
	@Column
	private				boolean				hasCollege;
}
