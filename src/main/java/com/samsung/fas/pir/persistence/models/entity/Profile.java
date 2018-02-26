package com.samsung.fas.pir.persistence.models.entity;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.persistence.models.entity.Authority;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "profile")
@DynamicUpdate
@DynamicInsert
public class Profile {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long					id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 					uuid;
	
	@Getter
	@Setter
	@Column(name="title", nullable=false, unique=true)
	private		String					title;
	
	@Getter
	@Setter
	@Column(name="description")
	private		String					description;
	
	@Getter
	@Setter
	@Column(name="status")
	private		boolean					active;
	
	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="created_by", updatable=false)
	private		User					whoCreated;
	
	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="modified_by")
	private		User					whoUpdated;
	
	@Getter
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at", updatable=false, nullable=false)
	private		Date					createdAt;
	
	@Getter
	@Setter
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", nullable=false)
	private		Date					updatedAt;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="profile")
	private 	Collection<Account>		accounts;

	@Getter
	@Setter
	@ManyToMany(mappedBy = "profiles", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private 	Collection<Authority>	authorities;
	
	@Getter
	@Setter
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "profile_id")
	private		Collection<Rule>		rules;

	public void addAuthority(Authority authority) {
		if (getAuthorities() != null) {
			if (getAuthorities().stream().filter(item -> item.getAuthority().equalsIgnoreCase(authority.getAuthority())).findFirst().orElse(null) == null) {
				getAuthorities().add(authority);
			}
		} else {
			setAuthorities(new HashSet<>());
			getAuthorities().add(authority);
		}
	}
}
