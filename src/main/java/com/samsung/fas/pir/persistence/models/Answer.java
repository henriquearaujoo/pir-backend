package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
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
public class Answer extends BaseID {
	@Getter
	@Setter
	@Column
	private 	long			mobileId;

	@Getter
	@Setter
	@Column
	@Alias("Descrição")
	private 	String			description;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	@Alias("Alternativa")
	private 	Alternative		alternative;

	@Getter
	@Setter
	@ManyToOne(optional = false)
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