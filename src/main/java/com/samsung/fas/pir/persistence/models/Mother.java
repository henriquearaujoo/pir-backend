package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.models.base.BaseNID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "mother")
@DynamicUpdate
@DynamicInsert
@Embeddable
@Alias("Mãe")
public class Mother extends BaseID {
	@Getter
	@Setter
//	@JoinColumn(name = "id")
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
	@OneToMany(mappedBy = "pregnant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Alias("Gestações")
	private 	Collection<Pregnancy>	pregnancies			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "mother", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Alias("Filhos")
	private 	Collection<Child>		children			= new ArrayList<>();
}