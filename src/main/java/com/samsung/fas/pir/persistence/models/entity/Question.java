package com.samsung.fas.pir.persistence.models.entity;

import com.samsung.fas.pir.persistence.models.enums.EQuestionType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity(name = "questions")
@Table(name = "questions")
@DynamicUpdate
@DynamicInsert
public class Question {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long			id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID			uuid;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "type", nullable = false)
	private 	EQuestionType 	type;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question", targetEntity = Answer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private 	Set<Answer>		answers;

	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="conclusion_fk")
	@LazyToOne(LazyToOneOption.PROXY)
	private 	Conclusion		conclusion;
}
