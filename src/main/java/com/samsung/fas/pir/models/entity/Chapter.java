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
	@Column(name="id")
	private		long			id;

	@Getter
	@Setter
	@Column(name = "number")
	private		int				chapter;

	@Getter
	@Setter
	@Column(name = "version")
	private 	int				version;

	@Getter
	@Setter
	@Column(name = "title")
	private 	String			title;

	@Getter
	@Setter
	@Column(name = "description")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "content")
	private		String			content;

	@Getter
	@Setter
	@Column(name = "purpose")
	private		String			purpose;

	@Getter
	@Setter
	@Column(name = "family_tasks")
	private		String			familyTasks;

	@Getter
	@Setter
	@Column(name = "estimated_time")
	private 	long			estimatedTime;

	@Getter
	@Setter
	@Column(name = "time_until_next")
	private 	long 			timeUntilNext;

	@Getter
	@Setter
	@Column(name = "in_use")
	private 	boolean			valid;

	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name = "intervention_id")
	private 	Intervention	intervention;

	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name = "greetings_id")
	private 	Greetings		greetings;
}
