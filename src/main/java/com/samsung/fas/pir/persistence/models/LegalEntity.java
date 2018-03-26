package com.samsung.fas.pir.persistence.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "entity", uniqueConstraints = @UniqueConstraint(columnNames = "cnpj", name = "cnpj"))
@DynamicUpdate
@DynamicInsert
public class LegalEntity {
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
	@Column(name="cnpj", length=14)
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