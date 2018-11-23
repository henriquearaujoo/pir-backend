package com.samsung.fas.pir.bi.persistence.community;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_community_service", columnList = "healthService, garbageDestination, waterSupply, hasElectricity, hasKindergarten, hasElementarySchool, hasHighSchool, hasCollege", unique = true))
public class CommunityServiceDimension extends BIBase {
	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				healthService;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				garbageDestination;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				waterSupply;

	@Getter
	@Setter
	@Column
	private				boolean				hasElectricity;

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
