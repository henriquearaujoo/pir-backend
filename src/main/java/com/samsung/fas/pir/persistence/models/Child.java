package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.EChildGender;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "child", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"id", "mother_id"}, name = "child_mother"),
	@UniqueConstraint(columnNames = {"mobile_id", "agent_id"}, name = "agent_child"),
})
@DynamicUpdate
@DynamicInsert
@Alias("Criança")
public class Child extends BaseID {
	@Getter
	@Setter
	@Column(name = "mobile_id")
	private		long			mobileId;

	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
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
	@Column(columnDefinition = "VARCHAR(100)")
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
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn
	@Alias("Mãe")
	private 	Responsible		mother;

	@Getter
	@Setter
	@OneToMany(mappedBy = "child", cascade = CascadeType.MERGE)
	@Alias("Visitas")
	private 	Collection<Visit>	visits;

	@Getter
	@Setter
	@ManyToMany(cascade = CascadeType.ALL)
	@Alias("Responsável")
	private 	Collection<Responsible>		responsibles;

	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn
	private 	User			agent;
}