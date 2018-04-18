package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.enums.EAnswerType;
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
@Table(name = "alternatives", uniqueConstraints = @UniqueConstraint(columnNames = {"description", "question_id"}, name = "answer"))
@DynamicUpdate
@DynamicInsert
public class Alternative extends BaseID {
	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "citext")
	private 	String					description;

	@Getter
	@Setter
	@Column(name = "type", nullable = false)
	private 	EAnswerType 			type;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Question				question;

	@Getter
	@Setter
	@OneToMany(mappedBy = "alternative")
	private 	Collection<Answer>		answers;
}