package com.samsung.fas.pir.models.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity(name="state")
public class State {
	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", updatable=false)
	private		long		id;
	
	@Getter
	@Setter
	@Column(name="name", nullable=false, unique=true)
	private		String		name;
	
	@Getter
	@Setter
	@Column(name="uf_abbr", nullable=false, unique=true)
	private		String		abbreviation;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="state", targetEntity=City.class)
	private		List<City>	cities;
	
}
