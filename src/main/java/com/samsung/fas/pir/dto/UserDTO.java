package com.samsung.fas.pir.dto;

import java.util.Date;
import java.util.UUID;

import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.City;
import com.samsung.fas.pir.models.User;
import com.samsung.fas.pir.models.user.Address;
import com.samsung.fas.pir.models.user.Organization;
import com.samsung.fas.pir.models.user.Person;

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
	private		Boolean			active;
	@JsonProperty("type")
	private		String			type;
	@JsonProperty("date")
	private		Date			registerDate;
	@JsonProperty("zipcode")
	private		String			postalCode;
	
//	public UserDTO(User m) {
//		id				= m.getId();
//		login			= m.getLogin();
//		password		= m.getPassword();
//		name			= m.getName();
//	}
	
	
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

	public Boolean isActive() {
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
		User 			user 			= new User();
		Address			addr			= new Address();
		Organization	org				= new Organization();
		Person			person			= new Person();
		
		// User relative
		user.setActive(active);
		user.setLogin(login);
		user.setName(name);
		user.setPassword(password);
		user.setType(type);
		
		// Person relative
		person.setCpf(cpf);
		person.setRg(rg);
		
		// ONG relative
		org.setCnpj(cnpj);
		org.setIe(ie);
		
		// Address relative
		addr.setCity(city);
		addr.setComplementAdress(complementAddress);
		addr.setNeighborhoodAddress(neighborhoodAddress);
		addr.setNumberAddress(numberAddress);
		addr.setPostalCode(postalCode);
		addr.setStreetAddress(streetNameAddress);
		
		// Set user attrs
		user.setAddress(addr);
		user.setOrganization(org);
		user.setPerson(person);
		
		return user;
	}
}
