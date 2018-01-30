package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="address")
@Table(name = "address")
@DynamicUpdate
@DynamicInsert
public class Address {
	@Getter
	@Setter
	@Id
	private 	long 			id;

	@Getter
	@Setter
	@OneToOne
	@MapsId
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
