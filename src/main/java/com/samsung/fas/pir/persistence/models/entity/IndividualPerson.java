package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="individual_person")
@Table(name = "individual_person")
@DynamicUpdate
@DynamicInsert
public class IndividualPerson {
	@Getter
	@Setter
	@Id
	private 	long 			id;

	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "id")
	private 	User			user;

	@Getter
	@Setter
	@Column(name="rg", length=32)
	private		String			rg;
	
	@Getter
	@Setter
	@Column(name="rg_emitter")
	private		String			emitter;
	
	@Getter
	@Setter
	@Column(name="cpf", length=11, unique=true)
	private		String			cpf;
}
