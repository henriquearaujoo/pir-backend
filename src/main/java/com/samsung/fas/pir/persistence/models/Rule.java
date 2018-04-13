package com.samsung.fas.pir.persistence.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name="profile_pages")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"profile_id", "page_id"}, name = "rule")})
@DynamicUpdate
@DynamicInsert
public class Rule {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long			id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 			uuid;

	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn
	private		Profile			profile;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private		Page			page;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private		boolean			canCreate;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private		boolean			canRead;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private		boolean			canUpdate;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private		boolean			canDelete;
}
