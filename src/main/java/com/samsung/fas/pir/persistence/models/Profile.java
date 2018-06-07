package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.configuration.security.persistence.models.Authority;
import com.samsung.fas.pir.persistence.enums.EProfileType;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "profile", uniqueConstraints = @UniqueConstraint(columnNames = "title", name = "profile_name"))
@DynamicUpdate
@DynamicInsert
public class Profile extends BaseID {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private		String					title;
	
	@Getter
	@Setter
	@Column
	private		String					description;
	
	@Getter
	@Setter
	@Column(name="status")
	private		boolean					active;

	@Getter
	@Setter
	@Column(/*nullable = false,*/ columnDefinition = "CITEXT")
	@Enumerated(EnumType.STRING)
	private 	EProfileType			type;
	
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
	private 	Collection<Authority>	authorities;

	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id")
	private		Collection<Rule>		rules;
}
