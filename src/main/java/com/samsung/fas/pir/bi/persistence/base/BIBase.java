package com.samsung.fas.pir.bi.persistence.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class BIBase {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OrderColumn
	private		Long			id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private		UUID			uuid;
}