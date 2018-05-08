package com.samsung.fas.pir.persistence.models.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseNID extends Base {
	@Getter
	@Setter
	@Id
	@Column
	private		long			id;
}
