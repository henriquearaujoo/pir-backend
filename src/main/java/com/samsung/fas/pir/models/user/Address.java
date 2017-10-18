package com.samsung.fas.pir.models.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.samsung.fas.pir.models.City;

@Embeddable
public class Address implements Serializable {
	@Column(name="neighborhood")
	private		String			neighborhoodAddress;
	
	@Column(name="street")
	private		String			streetAddress;
	
	@Column(name="complement")
	private		String			complementAdress;
	
	@Column(name="number")
	private		String			numberAddress;
	
	@Column(name="postal_code")
	private		String			postalCode;
	
	@ManyToOne
	@JoinColumn(name="city_id_fk")
//	@NotNull(message = "Invalid value for city field (NULL)")
//	@NotEmpty(message = "Invalid value for city field (EMPTY)")
	private		City			city;

	public String getNeighborhoodAddress() {
		return neighborhoodAddress;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getComplementAdress() {
		return complementAdress;
	}

	public String getNumberAddress() {
		return numberAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public City getCity() {
		return city;
	}

	public void setNeighborhoodAddress(String neighborhoodAddress) {
		this.neighborhoodAddress = neighborhoodAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public void setComplementAdress(String complementAdress) {
		this.complementAdress = complementAdress;
	}

	public void setNumberAddress(String numberAddress) {
		this.numberAddress = numberAddress;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
