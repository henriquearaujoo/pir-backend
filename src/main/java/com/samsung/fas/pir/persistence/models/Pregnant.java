package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.ECivilState;
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
@Table(name = "pregnant")
@DynamicUpdate
@DynamicInsert
@Alias("Criança")
public class Pregnant extends Base {
	@Getter
	@Setter
	@Column
	private		long						externalID;

	@Getter
	@Setter
	@Column(columnDefinition = "VARCHAR(10)")
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
	@Alias("Estado Civil")
	private 	ECivilState					civilState;

	@Getter
	@Setter
	@Column
	@Alias("Raça/Cor/Etinia")
	private 	String						ethnicity;

	@Getter
	@Setter
	@Column
	@Alias("Escolaridade")
	private 	String						scholarity;

	@Getter
	@Setter
	@Column
	@Alias("Contato - Telefone")
	private 	String						phoneNumber;

	@Getter
	@Setter
	@Column
	@Alias("Responsável - Telefone")
	private 	boolean						phoneOwner;

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
	private 	Agent						agent;

	@Getter
	@Setter
	@OneToMany(mappedBy = "pregnant", cascade = CascadeType.ALL)
	private 	Collection<Pregnancy>		pregnancies					= new ArrayList<>();
	// endregion
}