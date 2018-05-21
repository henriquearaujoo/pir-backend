package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "forms", uniqueConstraints = {@UniqueConstraint(name = "zone", columnNames = {"version", "age_zone"})})
@DynamicUpdate
@DynamicInsert
@Alias("Formulário")
public class Form extends BaseID {
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Versão")
	private 	int							version;

	@Getter
	@Setter
	@Column(name = "age_zone", nullable = false)
	@Alias("Faixa de Idade")
	private 	int							ageZone;

	@Getter
	@Setter
	@Column(name = "from_value", nullable = false)
	@Alias("De")
	private 	int							fromValue;

	@Getter
	@Setter
	@Column(name = "to_value", nullable = false)
	@Alias("Até")
	private 	int							toValue;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Em Anos")
	private 	boolean						inYears;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Ativo")
	private 	boolean						enabled;

	@Getter
	@Setter
	@OneToMany(orphanRemoval = true, mappedBy = "form")
	@Alias("Questões")
	private 	Collection<FormQuestion>	questions;

	@Getter
	@Setter
	@OneToMany(mappedBy = "form")
	@Alias("Visitas")
	private 	Collection<Visit>			visits;
}
