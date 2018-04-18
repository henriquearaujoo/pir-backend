package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "states")
public class State extends BaseID {
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
