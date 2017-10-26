package com.samsung.fas.pir.models.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(name="profile_page_rules")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"profile_id_fk", "page_id_fk"})})
@DynamicUpdate
@DynamicInsert
public class Rule {
	@Setter
	@Getter
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="id", updatable=false)
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
	@JoinColumn(name="created_by", nullable=false, updatable=false)
	private		User			whoCreated;
	
	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name="modified_by", nullable=false)
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
