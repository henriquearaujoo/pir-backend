package com.samsung.fas.pir.bi.persistence.base.common;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_name", columnList = "value", unique = true))
public class NameDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private 			String 				value;
}