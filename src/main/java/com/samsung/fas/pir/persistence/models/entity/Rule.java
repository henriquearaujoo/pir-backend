package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name="profile_pages")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"profile_id", "page_id"})})
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
	@JoinColumn(name="profile_id")
	private		Profile			profile;

	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="page_id")
	private		Page			page;

	@Getter
	@Setter
	@Column(name="can_create")
	private		boolean			create;

	@Getter
	@Setter
	@Column(name="can_view")
	private		boolean			read;

	@Getter
	@Setter
	@Column(name="can_update")
	private		boolean			update;

	@Getter
	@Setter
	@Column(name="can_delete")
	private		boolean			delete;
}
