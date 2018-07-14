package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.EGender;
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
@Table(name = "child", uniqueConstraints = @UniqueConstraint(name = "child_code", columnNames = {"code"}), indexes = @Index(name = "child_index", columnList = "code", unique = true))
@DynamicUpdate
@DynamicInsert
@Alias("Criança")
public class Child extends Base {
	@Getter
	@Setter
	@Transient
	private		long								externalID;

	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "VARCHAR(12)")
	@Alias("Matrícula")
	private 	String								code;

	@Getter
	@Setter
	@Column
	@Alias("Nome")
	private 	String								name;

	@Getter
	@Setter
	@Temporal(TemporalType.DATE)
	@Column
	@Alias("Data de Nascimento")
	private 	Date								birth;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column
	@Alias("Sexo")
	private		EGender								gender;

	@Getter
	@Setter
	@Column
	@Alias("Etnia - Raça - Cor")
	private 	String								ethnicity;

	@Getter
	@Setter
	@Column
	@Alias("Nome Completo da Mãe")
	private 	String								motherFullName;

	@Getter
	@Setter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	@Alias("Certidão de Nascimento - Registro")
	private 	boolean								registration;

	@Getter
	@Setter
	@Column(columnDefinition = "VARCHAR(15)")
	@Alias("Certidão de Nascimento - Registro")
	private 	String								susNumber;

	@Getter
	@Setter
	@Column
	@Alias("UB Frequentada")
	private 	String								frequentedUB;

	@Getter
	@Setter
	@Column
	@Alias("Prontuário UB")
	private 	String								recordUB;

	@Getter
	@Setter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	@Alias("Acompanhamento Pré-Natal")
	private 	boolean								prenatalCare;

	@Getter
	@Setter
	@Column
	@Alias("Tipo de Parto")
	private 	String								birthType;

	@Getter
	@Setter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	@Alias("Teve Episiotomia?")
	private 	boolean								episiotomy;

	@Getter
	@Setter
	@Column
	@Alias("Sangramento")
	private 	String								bleeding;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	@Alias("Deficiências")
	private 	String								deficiencies;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	@Alias("Intercorrência no Parto")
	private 	String								intercurrence;

	// region Relations
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_family"))
	@Alias("Família")
	private 	Family								family;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_agent"))
	private 	User								responsibleAgent;

	@Getter
	@Setter
	@OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
	@Alias("Visitas")
	private 	Collection<Visit>					visits					= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
	@Alias("Formulário - Respostas")
	private 	Collection<SAnswer>					answers					= new ArrayList<>();

//	@Getter
//	@Setter
//	@ManyToMany(mappedBy = "children", cascade = {CascadeType.PERSIST})
//	@Alias("Deficiência Congenita")
//	private 	Collection<CongenitalDeficiency>	deficiencies			= new ArrayList<>();
	// endregion
}