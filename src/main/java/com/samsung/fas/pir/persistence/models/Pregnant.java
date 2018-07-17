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
import java.util.Date;

@Entity
@Table(name = "pregnant", uniqueConstraints = @UniqueConstraint(name = "pregnant_code", columnNames = {"code"}), indexes = @Index(name = "pregnant_index", columnList = "code", unique = true))
@DynamicUpdate
@DynamicInsert
@Alias("Criança")
public class Pregnant extends Base {
	@Getter
	@Setter
	@Transient
	private		long						externalID;

	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "VARCHAR(12)")
	@Alias("Matrícula")
	private 	String						code;

	@Getter
	@Setter
	@Column
	@Alias("Nome")
	private 	String						name;

	@Getter
	@Setter
	@Temporal(TemporalType.DATE)
	@Column
	@Alias("Data de Nascimento")
	private 	Date						birth;

	@Getter
	@Setter
	@Column
	@Alias("Etnia - Raça - Cor")
	private 	String						ethnicity;

	@Getter
	@Setter
	@Column
	@Alias("Estado Civil")
	private 	String						civilState;

	@Getter
	@Setter
	@Column
	@Alias("Altura")
	private 	double						height;

	@Getter
	@Setter
	@Column(columnDefinition = "VARCHAR(20)")
	@Alias("Contato - Telefone")
	private 	String						phoneNumber;

	@Getter
	@Setter
	@Column
	@Alias("Responsável - Telefone")
	private 	String						phoneOwner;

	@Getter
	@Setter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	@Alias("Trabalha Fora de Casa")
	private 	boolean						workOutside;

	@Getter
	@Setter
	@Column
	@Alias("Escolaridade")
	private 	String 						scholarity;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	@Alias("Família - Casos")
	private 	String						illnessFamilyRegister;

	@Getter
	@Setter
	@Column
	@Alias("No. de Gestações Prévias")
	private 	int							previousPregnanciesCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Abortos")
	private 	int							abortionCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Partos Normais")
	private 	int							normalBirthCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Partos (Cesárea/Outros)")
	private 	int							cesareanBirthCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Nascidos Vivos")
	private 	int							bornAliveCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Crianças que Vivem")
	private 	int							childAliveCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Crianças que Morrem na 1a Semana")
	private 	int							deadFirstWeekCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Crianças que Morrem depois da 1a Semana")
	private 	int							deadAfterFirstWeekCount;

	@Getter
	@Setter
	@Column
	@Alias("No. de Crianças Nascidas Mortas")
	private 	int							bornDeadCount;

	// region Relations
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_family"))
	@Alias("Família")
	private 	Family						family;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_agent"))
	private 	User						responsibleAgent;

	@Getter
	@Setter
	@OneToMany(mappedBy = "pregnant", cascade = CascadeType.ALL)
	private 	Collection<Pregnancy>		pregnancies					= new ArrayList<>();
	// endregion
}