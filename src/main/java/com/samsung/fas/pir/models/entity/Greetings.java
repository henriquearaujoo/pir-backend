package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="greetings")
@Table(name = "greetings" /*,uniqueConstraints = @UniqueConstraint(columnNames= {"number", "version"})*/)
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
	@OneToOne(mappedBy = "greetings", targetEntity = Chapter.class)
	private 	Chapter			chapter;
}
