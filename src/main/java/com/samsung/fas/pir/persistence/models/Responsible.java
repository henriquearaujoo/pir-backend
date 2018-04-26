package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "responsible")
@DynamicUpdate
@DynamicInsert
public class Responsible extends BaseID {
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Nome")
	private 	String				name;

	@Getter
	@Setter
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@Alias("Nascimento")
	private 	Date				birth;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Em Programa Social")
	private 	boolean				inSocialProgram;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Alias("Tipo de Residência")
	private 	EHabitationType 	habitationType;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("No. de Pessoas")
	private 	int					habitationMembersCount;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Vive Com")
	private 	String				liveWith;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Fonte de Renda da Família")
	private 	String				familyIncome;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Participação na Renda")
	private 	String				incomeParticipation;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Tratamento de Água")
	private 	String				drinkingWaterTreatment;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Há Hospital Perto")
	private 	boolean				hasHospital;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Há Saneamento Básico ")
	private 	boolean				hasSanitation;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Há Tratamento de Água")
	private 	boolean				hasWaterTreatment;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Há outras Crianças")
	private 	boolean				familyHasChildren;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	@Alias("Observações")
	private 	String				observations;

	@Getter
	@Setter
	@OneToOne(mappedBy = "responsible", cascade = CascadeType.ALL, orphanRemoval = true)
	@Alias("Mãe")
	private 	Mother				mother;

	@Getter
	@Setter
	@OneToMany
	@Alias("Crianças")
	private 	Collection<Child>	children;

	@Getter
	@Setter
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn
	@Alias("Comunidade")
	private 	Community			community;

	@Getter
	@Setter
	@OneToMany(mappedBy = "responsible")
	@Alias("Visitas")
	private 	Collection<Visit>	visits;
}
