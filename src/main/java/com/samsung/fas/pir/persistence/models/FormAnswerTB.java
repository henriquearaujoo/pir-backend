package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "answer_type_b", uniqueConstraints = @UniqueConstraint(name = "question", columnNames = {"child_id", "question_id"}))
@DynamicUpdate
@DynamicInsert
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
