package com.samsung.fas.pir.models;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

@Entity(name="user")
public class User {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="id", unique=true)
	private		UUID			id;
	
	@Column(name="login", unique=true)
	private		String			login;
	
	@Column(name="password", length=20)
	private		String			password;
	
	@Column(name="full_name")
	private		String			name;
	
	@Column(name="rg", length=10, unique=true)
	private		String			rg;
	
	@Column(name="cpf", length=11, unique=true)
	private		String			cpf;
	
	@Column(name="cnpj", length=14, unique=true)
	private		String			cnpj;
	
	@Column(name="ie", unique=true)
	private		String			ie;
	
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
	
	@Column(name="status")
	private		boolean			active;
	
	@Column(name="type", length=10)
	private		String			type;
	
	@Column(name="dt_register")
	private		Date			registerDate;
	
	@ManyToOne
	@JoinColumn(name="city_id_fk")
	private		City			city;
	
	public UUID getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
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

	public String getIe() {
		return ie;
	}

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

	public boolean isActive() {
		return active;
	}

	public String getType() {
		return type;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public City getCity() {
		return city;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setIe(String ie) {
		this.ie = ie;
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

	public void setPostalCode(String zipCode) {
		this.postalCode = zipCode;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void copyFrom(Object source) {
		Iterable<Field>		props	= Arrays.asList(this.getClass().getDeclaredFields());
		BeanWrapper			srcwrap = PropertyAccessorFactory.forBeanPropertyAccess(source);
		BeanWrapper			trgwrap = PropertyAccessorFactory.forBeanPropertyAccess(this);
		props.forEach(p -> {
			if(!p.getName().contentEquals("id")) {
				trgwrap.setPropertyValue(p.getName(), srcwrap.getPropertyValue(p.getName()));
			}
		});
	}
}
