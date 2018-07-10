package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "alternatives", uniqueConstraints = @UniqueConstraint(columnNames = {"description", "question_id"}, name = "answer"))
@DynamicUpdate
@DynamicInsert
@Alias("Alternativa")
public class Alternative extends BaseID {
	@Getter
	@Setter
	@Column(columnDefinition = "citext")
	@Alias("Descrição")
	private 	String					description;

	@Getter
	@Setter
	@Column(name = "type")
	@Alias("Tipo")
	private 	EAnswerType 			type;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Questão")
	private 	Question				question;

	@Getter
	@Setter
	@OneToMany(mappedBy = "alternative")
	@Alias("Respostas")
	private 	Collection<Answer>		answers;
}