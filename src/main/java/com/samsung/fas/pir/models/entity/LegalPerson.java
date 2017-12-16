package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="legal_person")
@Table(name = "legal_person", uniqueConstraints= {@UniqueConstraint(columnNames= {"id", "guid"})})
@DynamicUpdate
@DynamicInsert
public class LegalPerson {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private 	long 			id;

	@Getter
	@Setter
	@Column(name="guid", updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private 	UUID 			guid;

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

	@Getter
	@Setter
	@OneToOne(mappedBy = "legalPerson", targetEntity = User.class)
	private 	User			user;
}