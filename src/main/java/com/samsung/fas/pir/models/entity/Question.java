package com.samsung.fas.pir.models.entity;

import com.samsung.fas.pir.models.enums.EQuestionType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "questions")
@Table(name = "questions")
@DynamicUpdate
@DynamicInsert
public class Question {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private		long			id;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "type", nullable = false)
	private 	EQuestionType	type;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question", targetEntity = Answer.class, fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	private 	Set<Answer>		answers;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="conclusion_fk")
	private 	Conclusion		conclusion;
}
