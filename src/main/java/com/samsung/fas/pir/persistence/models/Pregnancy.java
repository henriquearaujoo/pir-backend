package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "pregnancy")
@DynamicUpdate
@DynamicInsert
@Alias("Gestação")
public class Pregnancy extends Base {
	@Getter
	@Setter
	@Transient
	private		long						externalID;

	@Getter
	@Setter
	@Column
	@Alias("Peso Antes de Engravidar")
	private 	double						weightBeforePregnancy;

	// region Relations
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Gestante")
	private			Pregnant				pregnant;

	@Getter
	@Setter
	@ManyToMany(mappedBy = "pregnancies")
	@Alias("Complicações na Gravidez")
	private 	Collection<Complications>	complications		= new ArrayList<>();

	@Getter
	@Setter
	@ManyToMany(mappedBy = "pregnancies")
	@Alias("Imunização - Vacinas")
	private 	Collection<Vaccines>		vaccines			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "pregnancy", cascade = CascadeType.ALL)
	@Alias("Filhos")
	private 		Collection<SAnswer>		answers				= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "pregnancy", cascade = CascadeType.ALL)
	@Alias("Visitas")
	private			Collection<Visit>		visits				= new ArrayList<>();
	// endregion
}
