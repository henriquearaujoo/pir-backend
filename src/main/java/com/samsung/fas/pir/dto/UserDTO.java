package com.samsung.fas.pir.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.City;
import com.samsung.fas.pir.models.User;

/*
 * For create and update an user
 */
public class UserDTO {
	// Must
	@JsonProperty("login")
	private		String			login;
	@JsonProperty("password")
	private		String			password;
	
	// Optional
	@JsonProperty("id")
	private		UUID			id;
	@JsonProperty("name")
	private		String			name;
	@JsonProperty("rg")
	private		String			rg;
	@JsonProperty("cpf")
	private		String			cpf;
	@JsonProperty("cnpj")
	private		String			cnpj;
	@JsonProperty("ie")
	private		String			ie;
	@JsonProperty("neighborhood")
	private		String			neighborhoodAddress;
	@JsonProperty("street")
	private		String			streetNameAddress;
	@JsonProperty("complement")
	private		String			complementAddress;
	@JsonProperty("number")
	private		String			numberAddress;
	@JsonProperty("city")
	private		City			city;
	@JsonProperty("status")
	private		boolean			active;
	@JsonProperty("type")
	private		String			type;
	@JsonProperty("date")
	private		Date			registerDate;
	@JsonProperty("zipcode")
	private		String			postalCode;
	
	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRg() {
		return rg;
	}

	public String getCpf() {
		return cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getDistrictAddr() {
		return neighborhoodAddress;
	}

	public String getStreetNameAddr() {
		return streetNameAddress;
	}

	public String getComplementAddr() {
		return complementAddress;
	}

	public String getNumberAddr() {
		return numberAddress;
	}

	public City getCity() {
		return city;
	}

	public boolean isActive() {
		return active;
	}

	public String getType() {
		return type;
	}
	
	public String getIe() {
		return ie;
	}

	public String getNeighborhoodAddress() {
		return neighborhoodAddress;
	}

	public String getStreetNameAddress() {
		return streetNameAddress;
	}

	public String getComplementAddress() {
		return complementAddress;
	}

	public String getNumberAddress() {
		return numberAddress;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public User getModel() {
		User user = new User();
		user.setActive(active);
		user.setCity(city);
		user.setCnpj(cnpj);
		user.setComplementAdress(complementAddress);
		user.setCpf(cpf);
		user.setNeighborhoodAddress(neighborhoodAddress);
		user.setLogin(login);
		user.setName(name);
		user.setNumberAddress(numberAddress);
		user.setPassword(password);
		user.setRg(rg);
		user.setStreetAddress(streetNameAddress);
		user.setType(type);
		user.setRegisterDate(registerDate);
		user.setPostalCode(postalCode);
		return user;
	}
}
