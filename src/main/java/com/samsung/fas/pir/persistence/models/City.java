package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
@Alias("Município")
public class City extends BaseID {
	@Getter
	@Setter
	@Column(name="name", nullable=false)
	@Alias("Município")
	private		String		name;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="state_id_fk", nullable=false)
	@Alias("Estado")
	private		State		state;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="city", targetEntity=Address.class)
	@Alias("Usuários")
	private		List<User>		user;
}
