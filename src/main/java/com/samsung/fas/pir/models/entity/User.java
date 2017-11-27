package com.samsung.fas.pir.models.entity;

import com.samsung.fas.pir.models.entity.user.embedded.Address;
import com.samsung.fas.pir.models.entity.user.embedded.Organization;
import com.samsung.fas.pir.models.entity.user.embedded.Person;
import com.samsung.fas.pir.models.enums.EUserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity(name="user")
@Table(name = "user", uniqueConstraints= {@UniqueConstraint(columnNames= {"id", "guid"})})
@DynamicUpdate
@DynamicInsert
public class User implements Serializable {
	private static final long serialVersionUID = -2390821297865569815L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name="id", updatable=false, nullable = false, unique = true)
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private		UUID			id;

	@Getter
	@Setter
	@Column(name="guid", updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private		UUID			guid;

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
	@Column(name="email", nullable=false, unique = true)
	private		String			email;
	
	@Getter
	@Setter
	@Column(name="status", nullable=false)
	private		boolean			active;
	
	@Getter
	@Setter
	@Column(name="type", nullable=false)
	private EUserType type;
	
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
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_register", updatable=false, nullable=false)
	private 	Date 			registerDate;
	
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
