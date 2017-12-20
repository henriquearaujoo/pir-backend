package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="address")
@Table(name = "address", uniqueConstraints= {@UniqueConstraint(columnNames= {"id", "guid"})})
@DynamicUpdate
@DynamicInsert
public class Address {
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
	private		UUID			guid;

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
	@OneToOne(mappedBy = "address", targetEntity = User.class)
	private 	User			user;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="city_fk", nullable=false)
	private		City			city;
}
