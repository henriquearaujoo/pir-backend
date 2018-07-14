package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.EFormQuestionType;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "form_questions", uniqueConstraints = @UniqueConstraint(name = "question", columnNames = {"description", "form_id"}))
@DynamicUpdate
@DynamicInsert
@Alias("Questão do Formulário")
public class FormQuestion extends Base {
	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "citext")
	@Alias("Descrição")
	private 	String						description;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Alias("Tipo")
	private 	EFormQuestionType 			type;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Ativo")
	private 	boolean						enabled;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question")
	@Alias("Respostas Formulário A")
	private 	Collection<FormAnswerTA>	answersTA;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question")
	@Alias("Respostas Formulário B")
	private 	Collection<FormAnswerTB>	answersTB;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@Alias("Formulário")
	private 	Form						form;
}
