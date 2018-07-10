package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "address")
@DynamicUpdate
@DynamicInsert
@Alias("Endereço")
public class Address extends BaseID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@Alias("Usuário")
	private 	User			user;

	@Getter
	@Setter
	@Column(name="neighborhood", nullable=false)
	@Alias("Bairro")
	private		String			neighborhoodAddress;

	@Getter
	@Setter
	@Column(name="street", nullable=false)
	@Alias("Rua")
	private		String			streetAddress;

	@Getter
	@Setter
	@Column(name="complement")
	@Alias("Complemento")
	private		String			complementAddress;

	@Getter
	@Setter
	@Column(name="number", nullable=false)
	@Alias("Número")
	private		String			numberAddress;

	@Getter
	@Setter
	@Column(name="postal_code", length=32, nullable=false)
	@Alias("CEP")
	private		String			postalCode;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="city_fk", nullable=false)
	@Alias("Cidade")
	private		City			city;
}
