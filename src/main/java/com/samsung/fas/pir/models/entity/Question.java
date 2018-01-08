package com.samsung.fas.pir.models.entity;

import com.samsung.fas.pir.models.enums.EQuestionType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.bytecode.internal.javassist.FieldHandled;
import org.hibernate.bytecode.internal.javassist.FieldHandler;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "questions")
@Table(name = "questions")
@DynamicUpdate
@DynamicInsert
public class Question implements FieldHandled {
	private 	FieldHandler	handler;

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

	@Setter
	@OneToMany(mappedBy = "question", targetEntity = Answer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private 	Set<Answer>		answers;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="conclusion_fk")
	private 	Conclusion		conclusion;

	public Question() {
		super();
		answers = new HashSet<>();
	}

	public Set<Answer> getAnswers() {
		if (handler != null) {
			return (Set<Answer>) handler.readObject(this, "answers", answers);
		}
		return answers;
	}

	@Override
	public void setFieldHandler(FieldHandler handler) {
		this.handler = handler;
	}

	@Override
	public FieldHandler getFieldHandler() {
		return handler;
	}
}
