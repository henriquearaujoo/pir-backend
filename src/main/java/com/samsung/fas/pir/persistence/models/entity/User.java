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

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email", name = "email"))
@DynamicUpdate
@DynamicInsert
public class User implements Serializable {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long			id;

	@Setter
	@Getter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID			uuid;

	@Getter
	@Setter
	@Column(nullable=false)
	private		String			name;

	@Getter
	@Setter
	@Column(nullable=false)
	private		String			email;

	@Getter
	@Setter
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private 	EUserType 		type;

	@Getter
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false, nullable=false)
	private 	Date 			registerDate;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	private 	Person			person;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	private 	LegalEntity		entity;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	private		Address			address;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	private 	Account			account;
}
