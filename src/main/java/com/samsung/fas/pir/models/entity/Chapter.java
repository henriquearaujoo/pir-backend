package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="chapter")
@Table(name = "chapter", uniqueConstraints = @UniqueConstraint(columnNames= {"number", "version"}))
@DynamicUpdate
@DynamicInsert
public class Chapter {
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
	@Column(name = "title", nullable = false)
	private 	String			title;

	@Getter
	@Setter
	@Column(name = "subtitle")
	private		String			subtitle;

	@Getter
	@Setter
	@Column(name = "resources")
	private 	String			resources;

	@Getter
	@Setter
	@Column(name = "description", nullable = false)
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "content", nullable = false)
	private		String			content;

	@Getter
	@Setter
	@Column(name = "purpose", nullable = false)
	private		String			purpose;

	@Getter
	@Setter
	@Column(name = "family_tasks", nullable = false)
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

	@Getter
	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "chapter", targetEntity = Intervention.class)
//	@JoinColumn(name = "intervention_fk")
	private 	Intervention	intervention;

	@Getter
	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "chapter", targetEntity = Greetings.class)
//	@JoinColumn(name = "greetings_fk")
	private 	Greetings		greetings;

	@Getter
	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "chapter", targetEntity = Conclusion.class)
//	@JoinColumn(name = "conclusion_fk")
	private 	Conclusion		conclusion;
}
