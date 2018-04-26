package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseNID;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = "cpf", name = "cpf"))
@DynamicUpdate
@DynamicInsert
public class Person extends BaseNID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "id")
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
