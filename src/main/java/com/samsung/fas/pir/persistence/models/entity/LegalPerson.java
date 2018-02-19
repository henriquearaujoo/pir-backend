package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="legal_person")
@Table(name = "legal_person")
@DynamicUpdate
@DynamicInsert
public class LegalPerson {
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
	@Column(name="cnpj", length=14, unique=true)
	private		String			cnpj;
	
	@Getter
	@Setter
	@Column(name="ie", length=32)
	private		String			ie;

	@Getter
	@Setter
	@Column(name="fantasy_name")
	private		String			fantasyName;

	@Getter
	@Setter
	@Column(name="social_name")
	private		String			socialName;
}