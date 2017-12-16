package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name = "answers")
@Table(name = "answers")
@DynamicUpdate
@DynamicInsert
public class Answer {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private		long			id;

	@Getter
	@Setter
	@Column(name = "description", nullable = false)
	private 	String			description;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="question_fk")
	private 	Question		question;
}