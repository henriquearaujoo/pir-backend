package com.samsung.fas.pir.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="state")
public class State {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="state_id")
	private		long		id;
	@Column(name="state_name")
	private		String		name;
	@OneToMany(mappedBy="state", targetEntity=City.class)
	private		List<City>	cities;
	
	
}
