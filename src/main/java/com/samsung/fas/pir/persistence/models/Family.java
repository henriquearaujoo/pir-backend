package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "family", uniqueConstraints = @UniqueConstraint(name = "family_code", columnNames = {"code"}), indexes = @Index(name = "family_index", columnList = "code", unique = true))
@DynamicUpdate
@DynamicInsert
@Alias("Família")
public class Family extends Base {
	@Getter
	@Setter
	@Transient
	private		long					externalID;

	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "VARCHAR(10)")
	@Alias("Matrícula")
	private 	String					code;

	@Getter
	@Setter
	@Column
	@Alias("Líder da Família")
	private 	String					name;

	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	@Alias("Tipo de Residência")
	private 	EHabitationType 		habitationType;

	@Getter
	@Setter
	@Column
	@Alias("Fonte de Renda da Família")
	private 	String					familyIncome;

	@Getter
	@Setter
	@Column
	@Alias("Fonte de Renda da Família - Outros")
	private 	String					familyIncomeOther;

	@Getter
	@Setter
	@Column
	@Alias("No. de Membros")
	private 	int						membersCount;

	@Getter
	@Setter
	@Column
	@Alias("Tratamento de Água - Descrição")
	private 	String					waterTreatmentDescription;

	@Getter
	@Setter
	@Column
	@Alias("Há Unidade Básica Perto da Moradia")
	private 	boolean					nearbyUB;

	@Getter
	@Setter
	@Column
	@Alias("Há Saneamento Básico ")
	private 	boolean					sanitation;

	@Getter
	@Setter
	@Column
	@Alias("Tratamento de Água")
	private 	boolean					waterTreatment;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	@Alias("Observações")
	private 	String					observations;

	// region Relations
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_community"))
	@Alias("Comunidade")
	private 	Community				community;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_agent"))
	@Alias("Agente")
	private 	Agent					agent;

	@Getter
	@Setter
	@OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
	@Alias("Crianças")
	private 	Collection<Child> 		children			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
	@Alias("Gestações")
	private 	Collection<Pregnant>	pregnant			= new ArrayList<>();
	// endregion
}
