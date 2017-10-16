package com.samsung.fas.pir.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.City;
import com.samsung.fas.pir.models.User;

public class NewUserDTO {
	// Must
	@JsonProperty("login")
	private		String			login;
	@JsonProperty("passwd")
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
	@JsonProperty("district")
	private		String			districtAddr;
	@JsonProperty("street")
	private		String			streetNameAddr;
	@JsonProperty("complement")
	private		String			complementAddr;
	@JsonProperty("number")
	private		String			numberAddr;
	@JsonProperty("city")
	private		City			city;
	@JsonProperty("isacitve")
	private		boolean			active;
	@JsonProperty("type")
	private		String			type;
	
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
		return districtAddr;
	}

	public String getStreetNameAddr() {
		return streetNameAddr;
	}

	public String getComplementAddr() {
		return complementAddr;
	}

	public String getNumberAddr() {
		return numberAddr;
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
	
	public User getModel() {
		User user = new User();
		user.setActive(active);
		user.setCity(city);
		user.setCnpj(cnpj);
		user.setComplementAddr(complementAddr);
		user.setCpf(cpf);
		user.setDistrictAddr(districtAddr);
		user.setLogin(login);
		user.setName(name);
		user.setNumberAddr(numberAddr);
		user.setPassword(password);
		user.setRg(rg);
		user.setStreetNameAddr(streetNameAddr);
		user.setType(type);
		return user;
	}
}
