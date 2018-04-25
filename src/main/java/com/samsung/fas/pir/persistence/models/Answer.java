package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.utils.Alias;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "answers", uniqueConstraints = @UniqueConstraint(columnNames = {"child_id", "alternative_id", "mother_id", "question_id"}, name = "answer"))
@DynamicUpdate
@DynamicInsert
public class Answer extends BaseID {
	@Getter
	@Setter
	@Column
	@Alias("Descrição")
	private 	String			description;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	@Alias("Criança")
	private		Child			child;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	@Alias("Mãe")
	private		Mother			mother;

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