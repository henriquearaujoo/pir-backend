package com.samsung.fas.pir.bi.persistence.visit;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.bi.persistence.base.BIFactBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "fas_warehouse")
public class ChapterDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String				title;

	@Getter
	@Setter
	@Column
	private 			Short				number;
}