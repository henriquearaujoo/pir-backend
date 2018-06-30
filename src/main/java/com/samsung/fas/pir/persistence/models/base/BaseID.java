package com.samsung.fas.pir.persistence.models.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseID extends Base {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private		long			id			= -1;

	@Getter
	@Setter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private 	boolean			erased		= false;

	@Getter
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private 	Date			createdAt	= new Date();

	@Getter
	@Setter
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private 	Date			updatedAt	= new Date();

	@Override
	public boolean equals(Object obj) {
		try {
			return getUuid().compareTo(((BaseID) obj).getUuid()) == 0;
		} catch (Exception e) {
			return super.equals(obj);
		}
	}
}
