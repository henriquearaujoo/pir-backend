package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="profile_pages")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"profile", "page"})})
@DynamicUpdate
@DynamicInsert
public class Rule {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private		long			id;

	@Setter
	@Getter
	@Column(name="guid", updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private 	UUID			guid;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="profile")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private		Profile			profile;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="page")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
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
