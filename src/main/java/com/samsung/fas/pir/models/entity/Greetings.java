package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="greetings")
@Table(name = "greetings")
@DynamicUpdate
@DynamicInsert
public class Greetings {
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
	@Column(name = "eletronics", nullable = false)
	private 	boolean			eletronics;

	@Getter
	@Setter
	@Column(name = "sit", nullable = false)
	private 	boolean			sit;

	@Getter
	@Setter
	@Column(name = "goback", nullable = false)
	private 	boolean			goback;

	@Getter
	@Setter
	@Column(name = "stove", nullable = false)
	private 	boolean			stove;

	@Getter
	@Setter
	@OneToOne//(mappedBy = "greetings", targetEntity = Chapter.class)
	@JoinColumn(name = "chapter_fk")
	private 	Chapter			chapter;
}
