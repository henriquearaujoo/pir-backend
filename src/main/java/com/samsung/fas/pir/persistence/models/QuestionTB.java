package com.samsung.fas.pir.persistence.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "form_questions_type_b", uniqueConstraints = @UniqueConstraint(name = "question", columnNames = {"description", "form_id"}))
@DynamicUpdate
@DynamicInsert
public class QuestionTB {
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
	@Column(columnDefinition = "citext")
	private 	String			description;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private 	Boolean			isPresent;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	private 	Form			form;
}
