package com.samsung.fas.pir.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="profile")
public class Profile {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="prof_id", unique=true)
	private		UUID			id;
	
	@Column(name="prof_description", unique=true)
	private		String			description;
}
