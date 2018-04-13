package com.samsung.fas.pir.persistence.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "questions", uniqueConstraints = @UniqueConstraint(columnNames = {"conclusion_id", "description"}, name = "question"))
@DynamicUpdate
@DynamicInsert
public class Question {
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
	private 	UUID			uuid;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "citext")
	private 	String			description;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private 	Set<Answer>		answers;

	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn
	private 	Conclusion		conclusion;
}
