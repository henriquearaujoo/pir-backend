package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = "cpf", name = "cpf"))
@DynamicUpdate
@DynamicInsert
public class Person {
	@Getter
	@Setter
	@Id
	private 	long 			id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 			uuid;

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
	@Column(name="cpf", length=11)
	private		String			cpf;
}
