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
@Table(name = "form_questions_type_b", uniqueConstraints = @UniqueConstraint(name = "question", columnNames = {"description", "form_id"}))
@DynamicUpdate
@DynamicInsert
public class QuestionTB extends BaseID {
	@Getter
	@Setter
	@Column(columnDefinition = "citext")
	@Alias("Descrição")
	private 	String			description;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	@Alias("Presente")
	private 	Boolean			isPresent;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@Alias("Formulário")
	private 	Form			form;
}
