package com.samsung.fas.pir.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="user")
public class User {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="user_id", unique=true)
	private		UUID			id;
	@Column(name="user_login", unique=true)
	private		String			login;
	@Column(name="user_psswd", length=20)
	private		String			password;
	@Column(name="user_name")
	private		String			name;
	@Column(name="user_rg", length=10, unique=true)
	private		String			rg;
	@Column(name="user_cpf", length=11, unique=true)
	private		String			cpf;
	@Column(name="user_cnpj", length=14, unique=true)
	private		String			cnpj;
	@Column(name="user_addr_district")
	private		String			districtAddr;
	@Column(name="user_addr_street")
	private		String			streetNameAddr;
	@Column(name="user_addr_complement")
	private		String			complementAddr;
	@Column(name="user_addr_number")
	private		String			numberAddr;
	@ManyToOne
	@JoinColumn(name="city_id_fk")
	private		City			city;
	@Column(name="user_status")
	private		boolean			active;
	@Column(name="user_type", length=10)
	private		String			type;
	
	
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
	
	public void setDistrictAddr(String districtAddr) {
		this.districtAddr = districtAddr;
	}
	
	public void setStreetNameAddr(String streetNameAddr) {
		this.streetNameAddr = streetNameAddr;
	}
	
	public void setComplementAddr(String complementAddr) {
		this.complementAddr = complementAddr;
	}
	
	public void setNumberAddr(String numberAddr) {
		this.numberAddr = numberAddr;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	public void setActive(boolean ative) {
		this.active = ative;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
