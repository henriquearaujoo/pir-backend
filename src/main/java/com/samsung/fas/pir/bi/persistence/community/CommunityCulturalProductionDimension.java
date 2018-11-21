package com.samsung.fas.pir.bi.persistence.community;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_community_cultural", columnList = "value", unique = true))
public class CommunityCulturalProductionDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String				value;
}
