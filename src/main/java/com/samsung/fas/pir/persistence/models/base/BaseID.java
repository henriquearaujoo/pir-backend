package com.samsung.fas.pir.persistence.models.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseID extends Base {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long			id			= -1;

	@Getter
	@Setter
	@Column(name = "erased", columnDefinition = "DEFAULT FALSE")
	private 	boolean			erased		= false;
}
