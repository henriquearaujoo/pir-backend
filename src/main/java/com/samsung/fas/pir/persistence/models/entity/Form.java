package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "forms", uniqueConstraints = {@UniqueConstraint(name = "range", columnNames = "range"), @UniqueConstraint(name = "indicator", columnNames = {"from_value", "to_value"})})
@DynamicUpdate
@DynamicInsert
public class Form {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long					id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private		UUID					uuid;

	@Getter
	@Setter
	@Column(name = "from_value", nullable = false)
	private 	int						fromValue;

	@Getter
	@Setter
	@Column(name = "to_value", nullable = false)
	private 	int						toValue;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	int						range;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean					inYears;

	@Getter
	@Setter
	@OneToMany(orphanRemoval = true, mappedBy = "form")
	private 	Collection<QuestionTA>	questionsTA;

	@Getter
	@Setter
	@OneToMany(orphanRemoval = true, mappedBy = "form")
	private 	Collection<QuestionTB>	questionsTB;
}
