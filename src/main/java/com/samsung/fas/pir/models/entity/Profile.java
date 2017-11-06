package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name="profile")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"id", "guid"})})
@DynamicUpdate
@DynamicInsert
public class Profile implements Serializable{
	@Setter
	@Getter
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name="id", updatable=false, nullable = false, unique = true)
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private		UUID			id;

	@Column(name="guid", updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private 	UUID			guid;
	
	@Getter
	@Setter
	@Column(name="title", nullable=false, unique=true)
	private		String			title;
	
	@Getter
	@Setter
	@Column(name="description")
	private		String			description;
	
	@Getter
	@Setter
	@Column(name="status")
	private		boolean			active;
	
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
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at", updatable=false, nullable=false)
	private		Date			createdAt;
	
	@Getter
	@Setter
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", nullable=false)
	private		Date			updatedAt;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="profile", targetEntity=User.class)
	private		List<User>		users;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="profile", targetEntity=Rule.class)
	private		List<Rule>		rules;
}
