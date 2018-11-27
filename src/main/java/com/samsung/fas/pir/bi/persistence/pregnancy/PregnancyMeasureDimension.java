package com.samsung.fas.pir.bi.persistence.pregnancy;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_pregnancy_measure", columnList = "height, weight, planned", unique = true))
public class PregnancyMeasureDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				Short						height;

	@Getter
	@Setter
	@Column
	private				Short						weight;

	@Getter
	@Setter
	@Column
	private				Boolean						planned;
}