package com.samsung.fas.pir.persistence.models.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseNID extends Base {
	@Getter
	@Setter
	@Id
	private		long			id;
}
