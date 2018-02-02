package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
