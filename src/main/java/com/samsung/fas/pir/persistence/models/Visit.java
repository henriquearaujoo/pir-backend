package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.annotations.Alias;
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
	@Transient
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
	@Alias("Usuário")
	private 	User			agent;

	@Getter
	@Setter
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn
	@Alias("Responsável")
	private 	Responsible		responsible;

	@Getter
	@Setter
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
	@JoinColumn
	@Alias("Formulário")
	private 	Form			form;

	@Getter
	@Setter
	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
	@Alias("Respostas")
	private 	Collection<Answer>		answers;
}
