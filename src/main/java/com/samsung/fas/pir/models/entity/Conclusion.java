package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "conclusions")
@Table(name = "conslusions")
@DynamicUpdate
@DynamicInsert
public class Conclusion {
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
	@OneToMany(mappedBy = "conclusion", targetEntity = Question.class, fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	private 	Set<Question>	questions;

	@Getter
	@Setter
	@OneToOne//(mappedBy = "conclusion", targetEntity = Chapter.class)
	@JoinColumn(name = "conclusion_fk")
	private 	Chapter			chapter;
}
