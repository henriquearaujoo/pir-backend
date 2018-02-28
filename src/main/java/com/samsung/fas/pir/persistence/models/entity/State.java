package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name="state")
public class State {
	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", updatable=false)
	private		long		id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 		uuid;
	
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
