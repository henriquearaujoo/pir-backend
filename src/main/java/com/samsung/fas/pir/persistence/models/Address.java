package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseNID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@DynamicUpdate
@DynamicInsert
@Alias("Endereço")
public class Address extends BaseNID implements Serializable {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "id")
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
	private		String			complementAdress;

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
