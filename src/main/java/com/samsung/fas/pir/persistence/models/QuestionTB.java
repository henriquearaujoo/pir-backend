package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "form_questions_type_b", uniqueConstraints = @UniqueConstraint(name = "question", columnNames = {"description", "form_id"}))
@DynamicUpdate
@DynamicInsert
public class QuestionTB extends BaseID {
	@Getter
	@Setter
	@Column(columnDefinition = "citext")
	private 	String			description;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private 	Boolean			isPresent;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	private 	Form			form;
}
