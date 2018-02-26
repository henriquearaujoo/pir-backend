package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity(name = "conclusions")
@Table(name = "conslusions")
@DynamicUpdate
@DynamicInsert
public class Conclusion {
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
	@OneToMany(mappedBy = "conclusion", cascade = CascadeType.ALL, orphanRemoval = true)
	private 	Set<Question>	questions;

	@Getter
	@Setter
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@JoinColumn(name = "chapter_id")
	private 	Chapter			chapter;
}
