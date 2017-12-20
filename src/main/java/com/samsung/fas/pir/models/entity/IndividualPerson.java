package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="individual_person")
@Table(name = "individual_person", uniqueConstraints= {@UniqueConstraint(columnNames= {"id", "guid"})})
@DynamicUpdate
@DynamicInsert
public class IndividualPerson {
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

	@Getter
	@Setter
	@OneToOne(mappedBy = "individualPerson", targetEntity = User.class)
	private 	User			user;
}
