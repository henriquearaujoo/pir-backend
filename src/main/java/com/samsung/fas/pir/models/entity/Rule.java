package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity(name="profile_page_rules")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"profile_id_fk", "page_id_fk"})})
@DynamicUpdate
@DynamicInsert
public class Rule {
	@Setter
	@Getter
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name="id", updatable=false, nullable = false, unique = true)
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private		UUID			id;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="profile_id_fk")
	private		Profile			profile;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="page_id_fk")
	private		Page			page;
	
	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name="created_by", nullable=true, updatable=false)
	private		User			whoCreated;
	
	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name="modified_by", nullable=true)
	private		User			whoUpdated;
	
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
	
	@Getter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at", updatable=false, nullable=false)
	private		Date			createdAt;
	
	@Getter
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", nullable=false)
	private		Date			updatedAt;

}
