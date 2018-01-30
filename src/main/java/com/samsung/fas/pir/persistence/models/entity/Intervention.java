package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="intervention")
@Table(name = "intervention" /*,uniqueConstraints = @UniqueConstraint(columnNames= {"number", "version"})*/)
@DynamicUpdate
@DynamicInsert
public class Intervention {
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
	@Column(name = "activity", nullable = false, columnDefinition = "TEXT")
	private 	String			activity;

	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name = "chapter_fk")
	private 	Chapter			chapter;
}