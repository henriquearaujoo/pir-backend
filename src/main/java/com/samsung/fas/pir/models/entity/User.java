package com.samsung.fas.pir.models.entity;

import com.samsung.fas.pir.models.enums.EUserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity(name="user")
@Table(name = "user", uniqueConstraints= {@UniqueConstraint(columnNames= {"id", "guid"})})
@DynamicUpdate
@DynamicInsert
public class User {
	@Getter
	@Setter
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name="id")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private		UUID				id;

	@Getter
	@Setter
	@Column(name="guid", updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private		UUID				guid;
	
	@Getter
	@Setter
	@Column(name="full_name", nullable=false)
	private		String				name;

	@Getter
	@Setter
	@Column(name="email", nullable=false, unique = true)
	private		String				email;
	
	@Getter
	@Setter
	@Column(name="type", nullable=false)
	private 	EUserType 			type;

	@Getter
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_register", updatable=false, nullable=false)
	private 	Date 				registerDate;


	// region Foreigns

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "login_fk")
	private 	Login				login;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "individual_fk")
	private 	IndividualPerson	individualPerson;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "entity_fk")
	private 	LegalPerson 		legalPerson;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "address_fk")
	private		Address				address;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="profile_fk", nullable=false)
	private		Profile				profile;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="agent", targetEntity=Child.class)
	private		Set<Child>			children;

	// endregion
}
