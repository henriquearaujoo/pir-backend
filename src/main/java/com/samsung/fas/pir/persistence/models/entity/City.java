package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name="city")
public class City {
	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", updatable=false)
	private		long		id;
	
	@Getter
	@Setter
	@Column(name="name", nullable=false)
	private		String		name;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="state_id_fk", nullable=false)
	private		State		state;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="city", targetEntity=Address.class)
	private		List<User>		user;
}
