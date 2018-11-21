package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "answer_type_a", uniqueConstraints = @UniqueConstraint(name = "uk_answer_a", columnNames = {"child_id", "question_id"}))
@DynamicUpdate
@DynamicInsert
@Alias("Resposta Formulário A")
public class FormAnswerTA extends Base {
	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	@Alias("Realiza Sozinho")
	private 	boolean					canDoAlone;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	@Alias("Realiza com Ajuda")
	private 	boolean					canDoWithHelp;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	@Alias("Não Realiza")
	private 	boolean					canNotDo;

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
