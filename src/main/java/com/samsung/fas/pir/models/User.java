package com.samsung.fas.pir.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="user")
public class User {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="user_id")
	private		UUID			id;
	@Column(name="user_login")
	private		String			login;
	@Column(name="user_psswd")
	private		String			password;
	@Column(name="user_name")
	private		String			name;
	@Column(name="user_rg")
	private		String			rg;
	@Column(name="user_cpf")
	private		String			cpf;
	@Column(name="user_cnpj")
	private		String			cnpj;
	@Column(name="user_addr_district")
	private		String			districtAddr;
	@Column(name="user_addr_street")
	private		String			streetNameAddr;
	@Column(name="user_addr_complement")
	private		String			complementAddr;
	@Column(name="user_addr_number")
	private		String			numberAddr;
////	@ManyToOne
////	@JoinColumn(name="city_id_fk", nullable=false)
////	@Column(name="user_addr_state")
//	private		City			city;
	@Column(name="user_status")
	private		boolean			ative;
	@Column(name="user_type")
	private		String			type;
}
