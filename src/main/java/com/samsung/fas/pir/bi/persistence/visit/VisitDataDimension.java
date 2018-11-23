package com.samsung.fas.pir.bi.persistence.visit;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_chapter", columnList = "title, number, agentRating", unique = true))
public class VisitDataDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String				title;

	@Getter
	@Setter
	@Column
	private 			Short				number;

	@Getter
	@Setter
	@Column
	private				Short				agentRating;
}