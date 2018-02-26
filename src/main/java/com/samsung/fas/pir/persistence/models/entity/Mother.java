package com.samsung.fas.pir.persistence.models.entity;

import com.samsung.fas.pir.persistence.models.enums.ECivilState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "mother")
@DynamicUpdate
@DynamicInsert
public class Mother {
	@Getter
	@Setter
	@Id
	private 	long				id;

	@Getter
	@Setter
	@OneToOne(optional = false)
	@MapsId
	@JoinColumn(name = "id", updatable = false)
	private 	Responsible			responsible;

	@Getter
	@Setter
	@Column(insertable = false, updatable = false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID				uuid;

	// May mother have other children that are not registered
	@Getter
	@Setter
	@Column(nullable = false)
	private 	long				childrenCount;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private 	ECivilState 		civilState;

	@Getter
	@Setter
	@OneToMany(mappedBy = "mother")
	private		Collection<Child>	children;
}