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
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_community_city", columnList = "name, stateAbbreviation", unique = true))
public class CommunityCityDimension extends BIBase {
	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				name;

	@Getter
	@Setter
	@Length(min = 2, max = 2)
	@Column
	private				String				stateAbbreviation;
}
