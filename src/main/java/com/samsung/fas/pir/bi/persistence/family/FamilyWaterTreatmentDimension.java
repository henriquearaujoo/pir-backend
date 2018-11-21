package com.samsung.fas.pir.bi.persistence.family;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse")
public class FamilyWaterTreatmentDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String				value;
}