package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email", name = "email"))
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate
@DynamicInsert
@Alias("Usuário")
public class User extends Base {
	@Getter
	@Setter
	@Alias("Nome")
	private		String					name;

	@Getter
	@Setter
	@Alias("Email")
	private		String					email;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@Alias("PF")
	private 	Person					person;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@Alias("PF")
	private 	Agent					agent;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@Alias("PJ")
	private 	LegalEntity				entity;

	@Getter
	@Setter
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user", orphanRemoval = true)
	@Alias("Endereço")
	private		Address					address;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@Alias("Conta")
	private 	Account 				account;
}