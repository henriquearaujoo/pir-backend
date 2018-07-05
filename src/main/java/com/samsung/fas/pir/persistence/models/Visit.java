package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "visits")
@DynamicUpdate
@DynamicInsert
@Alias("Visita")
public class Visit extends BaseID {
	@Getter
	@Setter
	@Column(name = "mobile_id")
	private 	long			mobileId;

	@Getter
	@Setter
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, nullable = false)
	@Alias("Realizada Em")
	private 	Date			doneAt;

	@Getter
	@Setter
	@Column
	@Alias("Número")
	private 	int				number;

	@Getter
	@Setter
	@Column
	@Alias("Avaliação da Família")
	private 	short			familyRating;

	@Getter
	@Setter
	@Column
	@Alias("Avaliação do Agente")
	private 	short			agentRating;

	@Getter
	@Setter
	@Column
	@Alias("Duração")
	private 	long			duration;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Agente")
	private 	User			agent;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	@Alias("Mãe - Gravidez")
	private 	Pregnancy		pregnancy;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	@Alias("Criança")
	private 	Child			child;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Capítulo")
	private 	Chapter			chapter;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_form"))
	@Alias("Formulário")
	private 	Form			form;

	@Getter
	@Setter
	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
	@Alias("Respostas")
	private 	Collection<Answer>		answers;
}
