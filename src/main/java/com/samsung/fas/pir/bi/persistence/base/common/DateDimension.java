package com.samsung.fas.pir.bi.persistence.base.common;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "date", schema = "fas_warehouse", indexes = @Index(name = "idx_date", unique = true, columnList = "datetime"))
public class DateDimension extends BIBase {
	@Getter
	@Setter
	@Temporal(TemporalType.DATE)
	@Column
	private 		Date			datetime;

	@Getter
	@Setter
	@Column
	private			Short			day;

	@Getter
	@Setter
	@Column
	private			Short			weekDay;

	@Getter
	@Setter
	@Column
	private			Short			monthDay;

	@Getter
	@Setter
	@Column
	private			Short			week;

	@Getter
	@Setter
	@Column
	private			Short			month;

	@Getter
	@Setter
	@Column
	private			Short			quarter;

	@Getter
	@Setter
	@Column
	private 		Short			semester;

	@Getter
	@Setter
	@Column
	private 		Short			year;
}