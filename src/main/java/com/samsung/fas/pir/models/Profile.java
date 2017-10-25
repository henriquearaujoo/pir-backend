package com.samsung.fas.pir.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(name="profile")
@DynamicUpdate
@DynamicInsert
public class Profile {
	@Setter
	@Getter
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="id", updatable=false)
	private		UUID			id;
	
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
	@JoinColumn(name="created_by", nullable=false, updatable=false)
	private		User			whoCreated;
	
	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name="modified_by", nullable=false)
	private		User			whoUpdated;
	
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
	
	@OneToMany(mappedBy="profile", targetEntity=User.class)
	private		List<User>		users;
}
