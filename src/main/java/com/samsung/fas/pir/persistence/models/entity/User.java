package com.samsung.fas.pir.persistence.models.entity;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.models.enums.EUserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(name="user")
@Table(name = "user")
@DynamicUpdate
@DynamicInsert
public class User implements Serializable {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long				id;

	@Setter
	@Getter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID				uuid;

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
	@Enumerated(EnumType.STRING)
	private 	EUserType 			type;

	@Getter
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_register", updatable=false, nullable=false)
	private 	Date 				registerDate;

	//////////
	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	private 	IndividualPerson	individual;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	private 	LegalPerson			legal;
	//////////

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	private		Address				address;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	private 	Account				account;
}
