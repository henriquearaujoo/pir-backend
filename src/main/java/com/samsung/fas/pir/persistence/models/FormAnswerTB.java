package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "answer_type_b", uniqueConstraints = @UniqueConstraint(name = "question", columnNames = {"child_id", "question_id"}))
@DynamicUpdate
@DynamicInsert
@Alias("Resposta Formulário B")
public class FormAnswerTB extends BaseID {
	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	@Alias("Presente")
	private 	Boolean					isPresent;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Questão")
	private 	FormQuestion			question;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Criança")
	private 	Child					child;
}
