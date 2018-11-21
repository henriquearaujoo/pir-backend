package com.samsung.fas.pir.bi.persistence.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BIFactBase extends BIBase {
	@Getter
	@Setter
	@Column
	private			double			counter;
}
