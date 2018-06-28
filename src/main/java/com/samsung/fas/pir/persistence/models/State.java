package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "states")
@Alias("Estado")
public class State extends BaseID {
	@Getter
	@Setter
	@Column(name="name", nullable=false, unique=true)
	@Alias("Estado")
	private		String			name;
	
	@Getter
	@Setter
	@Column(name="uf_abbr", columnDefinition = "CITEXT", nullable=false, unique=true)
	@Alias("Abreviação")
	private		String			abbreviation;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="state", targetEntity=City.class)
	@Alias("Cidades")
	private 	Collection<City> cities;
	
}
