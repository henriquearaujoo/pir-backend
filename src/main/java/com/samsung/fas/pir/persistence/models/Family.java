package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "family")
@DynamicUpdate
@DynamicInsert
@Alias("Família")
public class Family extends Base {
	@Getter
	@Setter
	private		long					externalID;

	@Getter
	@Setter
	@Column(columnDefinition = "VARCHAR(10)")
	@Alias("Matrícula")
	private 	String					code;

	@Getter
	@Setter
	@Column
	@Alias("Líder da Família")
	private 	String					name;

	@Getter
	@Setter
	@Temporal(TemporalType.DATE)
	@Column
	@Alias("Data de Nascimento")
	private		Date					birth;

	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	@Alias("Gênero")
	private		EGender					gender;

	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	@Alias("Gênero")
	private		ECivilState				civilState;

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
	private 	String					income;

	@Getter
	@Setter
	@Column
	@Alias("Fonte de Renda da Família - Outros")
	private 	String					incomeOther;

	@Getter
	@Setter
	@Column
	@Alias("No. de Membros")
	private 	short					membersCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Crianças")
	private 	short					childrenCount;

	@Getter
	@Setter
	@Column
	@Alias("Tratamento de Água - Descrição")
	private 	String					waterTreatmentDescription;

	@Getter
	@Setter
	@Column
	@Alias("Participa de Programa Social")
	private 	boolean					socialProgram;


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
	@JoinColumn(name = "agent_person_user_id", foreignKey = @ForeignKey(name = "relation_agent"))
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
