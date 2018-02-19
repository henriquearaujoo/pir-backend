package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name="intervention")
@Table(name = "intervention" /*,uniqueConstraints = @UniqueConstraint(columnNames= {"number", "version"})*/)
@DynamicUpdate
@DynamicInsert
public class Intervention {
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