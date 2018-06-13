package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = "cpf", name = "cpf"))
@DynamicUpdate
@DynamicInsert
@Alias("Pessoa Física")
public class Person extends BaseID {
	@Getter
	@Setter
	@OneToOne(optional = false)
//	@JoinColumn(name = "id")
	@MapsId
	@Alias("Usuário")
	private 	User			user;

	@Getter
	@Setter
	@Column(name="rg", length=32)
	@Alias("RG")
	private		String			rg;
	
	@Getter
	@Setter
	@Column(name="rg_emitter")
	@Alias("Emissor RG")
	private		String			emitter;
	
	@Getter
	@Setter
	@Column(name="cpf", length=11)
	@Alias("CPF")
	private		String			cpf;
}
