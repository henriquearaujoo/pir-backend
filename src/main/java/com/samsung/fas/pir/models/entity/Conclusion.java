package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.bytecode.internal.javassist.FieldHandled;
import org.hibernate.bytecode.internal.javassist.FieldHandler;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "conclusions")
@Table(name = "conslusions")
@DynamicUpdate
@DynamicInsert
public class Conclusion implements FieldHandled {
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

	@Setter
	@OneToMany(mappedBy = "conclusion", targetEntity = Question.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private 	Set<Question>	questions;

	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name = "fk_chapter")
	private 	Chapter			chapter;

	public Conclusion() {
		super();
		questions = new HashSet<>();
	}

	public Set<Question> getQuestions() {
		if (handler != null) {
			return (Set<Question>) handler.readObject(this, "questions", questions);
		}
		return questions;
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
