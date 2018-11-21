package com.samsung.fas.pir.bi.persistence.base.common;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse", indexes = {
	@Index(name = "idx_agent_name", columnList = "name"),
	@Index(name = "idx_agent_cpf", unique = true, columnList = "cpf")
})
public class AgentDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String					name;

	@Getter
	@Setter
	@Length(max = 11)
	@Column
	private				String					cpf;

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
