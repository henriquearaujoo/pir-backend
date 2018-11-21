package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "person", uniqueConstraints = {@UniqueConstraint(name = "uk_cpf", columnNames = {"cpf"})}, indexes = @Index(name = "idx_cpf", columnList = "cpf"))
@DynamicUpdate
@DynamicInsert
@Alias("Pessoa Física")
public class Person extends Base {
	@Getter
	@Setter
	@Column(name="cpf", length=11)
	@Alias("CPF")
	private		String			cpf;

	// region Relations
	@Getter
	@Setter
	@OneToOne(optional = false)
	@MapsId
	@Alias("Usuário")
	private 	User			user;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
	@Alias("PF")
	private 	Agent			agent;
	// endregion
}
