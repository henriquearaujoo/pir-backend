package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "responsible")
@DynamicUpdate
@DynamicInsert
@Alias("Responsável")
public class Responsible extends BaseID {
	@Getter
	@Setter
	@Column(name = "mobile_id")
	private		long				mobileId;

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
	@Column
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
	@Column
	@Alias("Vive Com")
	private 	String				liveWith;

	@Getter
	@Setter
	@Column
	@Alias("Fonte de Renda da Família")
	private 	String				familyIncome;

	@Getter
	@Setter
	@Column
	@Alias("Participação na Renda")
	private 	String				incomeParticipation;

	@Getter
	@Setter
	@Column
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
	@Column(nullable = false)
	@Alias("No. de Filhos")
	private 	long				childrenCount;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Alias("Estado Civil")
	private 	ECivilState 		civilState;

	@Getter
	@Setter
	@ManyToOne
	private 	User				agent;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Comunidade")
	private 	Community			community;

	@Getter
	@Setter
	@OneToOne(mappedBy = "responsible", cascade = CascadeType.ALL, orphanRemoval = true)
	@Alias("Mãe")
	private 	Mother				mother;

	@Getter
	@Setter
	@ManyToMany(mappedBy = "responsible", cascade = CascadeType.ALL)
	@Alias("Crianças")
	private 	Collection<Child>	children			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "responsible", cascade = CascadeType.ALL)
	@Alias("Filhos")
	private 	Collection<SAnswer>		answers				= new ArrayList<>();
}
