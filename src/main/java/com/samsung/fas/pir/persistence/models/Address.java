package com.samsung.fas.pir.persistence.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "address")
@DynamicUpdate
@DynamicInsert
public class Address implements Serializable {
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
	@Column(name="neighborhood", nullable=false)
	private		String			neighborhoodAddress;

	@Getter
	@Setter
	@Column(name="street", nullable=false)
	private		String			streetAddress;

	@Getter
	@Setter
	@Column(name="complement")
	private		String			complementAdress;

	@Getter
	@Setter
	@Column(name="number", nullable=false)
	private		String			numberAddress;

	@Getter
	@Setter
	@Column(name="postal_code", length=32, nullable=false)
	private		String			postalCode;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="city_fk", nullable=false)
	private		City			city;
}
