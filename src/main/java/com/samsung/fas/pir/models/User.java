package com.samsung.fas.pir.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
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
	@Column(name="user_is_active")
	private		boolean			ative;
	@Column(name="user_type")
	private		String			type;
	
}
