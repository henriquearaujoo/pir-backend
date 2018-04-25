package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.enums.EChildGender;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.utils.Alias;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "child")
@DynamicUpdate
@DynamicInsert
public class Child extends BaseID {
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Nome")
	private 	String			name;

	@Getter
	@Setter
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@Alias("Data de Nascimento")
	private 	Date			birth;

	@Getter
	@Setter
	@Column
	@Alias("Nome do Pai")
	private 	String			fatherName;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column
	@Alias("Sexo")
	private 	EChildGender 	gender;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Registro Civil")
	private 	boolean			hasCivilRegistration;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	@Alias("Justificativa de Registro Civil")
	private 	String			civilRegistrationJustificative;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Dificuldades Educacionais")
	private 	boolean			hasEducationDifficulty;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	@Alias("Descrição das Dificuldades Educacionais")
	private 	String			educationDifficultySpecification;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Nasceu Prematuramente")
	private 	boolean			prematureBorn;

	@Getter
	@Setter
	@Column
	@Alias("Semana do Nascimento Prematuro")
	private 	int				bornWeek;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Quem Cuida")
	private 	String			whoTakeCare;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Brinca com Quem")
	private 	String			playsWithWho;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Medição Mensal de Peso")
	private 	boolean			mensalWeight;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Participa de Programas Educacionais")
	private 	boolean			socialEducationalPrograms;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Vacinação em Dia")
	private 	boolean			vacinationUpToDate;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Dificuldade de Relação")
	private 	boolean			relationDifficulties;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn
	@Alias("Mãe")
	private 	Responsible		mother;

	@Getter
	@Setter
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn
	@Alias("Responsável")
	private 	Responsible		responsible;

	@Getter
	@Setter
	@OneToMany(mappedBy = "child")
	@Alias("Respostas")
	private 	Collection<Answer>	answers;

	@Getter
	@Setter
	@OneToMany(mappedBy = "child", cascade = CascadeType.MERGE)
	@Alias("Visitas")
	private 	Collection<Visit>	visits;
}