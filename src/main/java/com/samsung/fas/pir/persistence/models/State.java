package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "states")
@Alias("Estado")
public class State extends BaseID {
	@Getter
	@Setter
	@Column(name="name", nullable=false, unique=true)
	@Alias("Estado")
	private		String		name;
	
	@Getter
	@Setter
	@Column(name="uf_abbr", nullable=false, unique=true)
	@Alias("Abreviação")
	private		String		abbreviation;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="state", targetEntity=City.class)
	@Alias("Cidades")
	private		List<City>	cities;
	
}
