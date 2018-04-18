package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.enums.EFormQuestionType;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "form_questions", uniqueConstraints = @UniqueConstraint(name = "question", columnNames = {"description", "form_id"}))
@DynamicUpdate
@DynamicInsert
public class FormQuestion extends BaseID {
	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "citext")
	private 	String						description;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private 	EFormQuestionType 			type;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean						enabled;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question")
	private 	Collection<FormAnswerTA>	answersTA;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question")
	private 	Collection<FormAnswerTB>	answersTB;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	private 	Form						form;
}
