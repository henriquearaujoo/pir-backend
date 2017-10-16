package com.samsung.fas.pir.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="city")
public class City {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="state_id")
	private		long		id;
	@Column(name="city_name")
	private		String		name;
	@ManyToOne
	@JoinColumn(name="state_id_fk", nullable=false)
	private		State		state;
	@OneToMany(mappedBy="city", targetEntity=User.class)
	private		List<User>		user;
}
