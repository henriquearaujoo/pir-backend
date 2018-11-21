package com.samsung.fas.pir.bi.persistence.base.common;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse")
public class AgentDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String					name;

	@Getter
	@Setter
	@Column
	private 			String					community;

	@Getter
	@Setter
	@Column
	private				String					unity;

	@Getter
	@Setter
	@Column
	private 			String					regional;

	@Getter
	@Setter
	@Column
	private 			String					city;
}
