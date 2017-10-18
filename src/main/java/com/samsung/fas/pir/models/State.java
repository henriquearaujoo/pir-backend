package com.samsung.fas.pir.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="state")
public class State {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private		long		id;
	
	@Column(name="name")
	@NotNull(message = "Invalid value for name field (NULL)")
	@NotEmpty(message = "Invalid value for name field (EMPTY)")
	private		String		name;
	
	@Column(name="fu")
	@NotNull(message = "Invalid value for f.u. field (NULL)")
	@NotEmpty(message = "Invalid value for f.u. field (EMPTY)")
	private		String		abbreviation;
	
	@OneToMany(mappedBy="state", targetEntity=City.class)
	private		List<City>	cities;
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<City> getCities() {
		return cities;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}
