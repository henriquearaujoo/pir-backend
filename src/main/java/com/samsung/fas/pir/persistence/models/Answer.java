package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.enums.EAnswerType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "answers", uniqueConstraints = @UniqueConstraint(columnNames = {"description", "question_id"}, name = "answer"))
@DynamicUpdate
@DynamicInsert
public class Answer {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long			id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 			uuid;

	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "citext")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "type", nullable = false)
	private 	EAnswerType 	type;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Question		question;
}