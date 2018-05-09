package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email", name = "email"))
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate
@DynamicInsert
@Alias("Usuário")
public class User extends BaseID implements Serializable {
	@Getter
	@Setter
	@Column(nullable=false)
	@Alias("Nome")
	private		String			name;

	@Getter
	@Setter
	@Column(nullable=false)
	@Alias("Email")
	private		String			email;

	@Getter
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false, nullable=false)
	@Alias("Data de Registro")
	private 	Date 			registerDate;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	@Alias("PF")
	private 	Person			person;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	@Alias("PJ")
	private 	LegalEntity		entity;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	@Alias("Endereço")
	private		Address			address;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	@Alias("Conta")
	private 	Account 		account;

	// region Agent
	@Getter
	@Setter
	@Column
	@Alias("Latitude")
	private 	Double			latitude;

	@Getter
	@Setter
	@Column
	@Alias("Longitude")
	private 	Double			longitude;

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	private 	Collection<Child>		children;

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	private 	Collection<Responsible>	responsibles;

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	@Alias("Visitas")
	private		Collection<Visit>		visits;
	// endregion
}
