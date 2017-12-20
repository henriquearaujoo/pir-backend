package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.bytecode.internal.javassist.FieldHandled;
import org.hibernate.bytecode.internal.javassist.FieldHandler;

import javax.persistence.*;

// TODO: Change mapping
@Entity(name="chapter")
@Table(name = "chapter", uniqueConstraints = @UniqueConstraint(columnNames= {"number", "version"}))
@DynamicUpdate
@DynamicInsert
public class Chapter implements FieldHandled {
	private 	FieldHandler	handler;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private		long			id;

	@Getter
	@Setter
	@Column(name = "number", nullable = false)
	private		int				chapter;

	@Getter
	@Setter
	@Column(name = "version", nullable = false)
	private 	int				version;

	@Getter
	@Setter
	@Column(name = "title", nullable = false, columnDefinition = "TEXT")
	private 	String			title;

	@Getter
	@Setter
	@Column(name = "subtitle", nullable = false, columnDefinition = "TEXT")
	private		String			subtitle;

	@Getter
	@Setter
	@Column(name = "resources", nullable = false, columnDefinition = "TEXT")
	private 	String			resources;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private		String			content;

	@Getter
	@Setter
	@Column(name = "purpose", nullable = false, columnDefinition = "TEXT")
	private		String			purpose;

	@Getter
	@Setter
	@Column(name = "family_tasks", nullable = false, columnDefinition = "TEXT")
	private		String			familyTasks;

	@Getter
	@Setter
	@Column(name = "estimated_time", nullable = false)
	private 	long			estimatedTime;

	@Getter
	@Setter
	@Column(name = "time_until_next", nullable = false)
	private 	long 			timeUntilNext;

	@Getter
	@Setter
	@Column(name = "in_use", nullable = false)
	private 	boolean			valid;

	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chapter", targetEntity = Intervention.class)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private 	Intervention	intervention;

	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chapter", targetEntity = Greetings.class)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private 	Greetings		greetings;

	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chapter", targetEntity = Conclusion.class)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private 	Conclusion		conclusion;

	// region Lazy Getters
	public Intervention getIntervention() {
		if (handler != null) {
			return (Intervention) handler.readObject(this, "intervention", intervention);
		}
		return intervention;
	}

	public Greetings getGreetings() {
		if (handler != null) {
			return (Greetings) handler.readObject(this, "greetings", greetings);
		}
		return greetings;
	}

	public Conclusion getConclusion() {
		if (handler != null) {
			return (Conclusion) handler.readObject(this, "conclusion", conclusion);
		}
		return conclusion;
	}
	// endregion

	@Override
	public void setFieldHandler(FieldHandler fieldHandler) {
		handler = fieldHandler;
	}

	@Override
	public FieldHandler getFieldHandler() {
		return handler;
	}
}