package com.samsung.fas.pir.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.samsung.fas.pir.enums.UserType;
import com.samsung.fas.pir.models.user.embedded.Address;
import com.samsung.fas.pir.models.user.embedded.Organization;
import com.samsung.fas.pir.models.user.embedded.Person;

import lombok.Getter;
import lombok.Setter;

@Entity(name="user")
public class User implements Serializable {
	private static final long serialVersionUID = -2390821297865569815L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="id", updatable=false)
	private		UUID			id;
	
	@Getter
	@Setter
	@Column(name="login", unique=true, nullable=false)
	private		String			login;
	
	@Getter
	@Setter
	@Column(name="password", nullable=false)
	private		String			password;
	
	@Getter
	@Setter
	@Column(name="full_name", nullable=false)
	private		String			name;
	
	@Getter
	@Setter
	@Column(name="status", nullable=false)
	private		boolean			active;
	
	@Getter
	@Setter
	@Column(name="type", nullable=false)
	private		UserType		type;
	
	@Getter
	@Setter
	@Embedded
	private		Address			address;
	
	@Getter
	@Setter
	@Embedded	
	private		Person			person;
	
	@Getter
	@Setter
	@Embedded	
	private		Organization	organization;
	
	@Getter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_register", updatable=false, nullable=false)
	private		Date			registerDate;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="profile_id_fk", nullable=false)
	private		Profile			profile;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="agent", targetEntity=Child.class)
	private		Set<Child>		children;
}
