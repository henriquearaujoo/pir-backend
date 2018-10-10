package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "answers", uniqueConstraints = @UniqueConstraint(columnNames = {"alternative_id", "question_id", "visit_id"}, name = "answer"))
@DynamicUpdate
@DynamicInsert
@Alias("Resposta")
public class Answer extends Base {
	@Getter
	@Setter
	@Column
	private 	long			externalID;

	@Getter
	@Setter
	@Column
	@Alias("Descrição")
	private 	String			description;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_agent"))
	private 	Agent			agent;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	@Alias("Alternativa")
	private 	Alternative		alternative;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	@Alias("Questão")
	private 	Question		question;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Visita")
	private 	Visit			visit;
}