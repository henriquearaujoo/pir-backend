package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "mother")
@DynamicUpdate
@DynamicInsert
@Alias("Mãe")
public class Mother extends BaseID {
	@Getter
	@Setter
	@OneToOne(optional = false)
	@MapsId
	@Alias("Responsável")
	private 	Responsible				responsible;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Está Grávida")
	private 	boolean					pregnant;

	@Getter
	@Setter
	@OneToMany(mappedBy = "pregnant", cascade = CascadeType.ALL)
	@Alias("Gestações")
	private 	Collection<Pregnancy>	pregnancies			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "mother", cascade = CascadeType.ALL)
	@Alias("Filhos")
	private 	Collection<Child>		children			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "mother", cascade = CascadeType.ALL)
	@Alias("Filhos")
	private 	Collection<SAnswer>		answers				= new ArrayList<>();
}