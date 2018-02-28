package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name="greetings")
@Table(name = "greetings")
@DynamicUpdate
@DynamicInsert
public class Greetings {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long			id;

	@Getter
	@Setter
	@Column(insertable = false, updatable = false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 			uuid;

	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private 	Chapter			chapter;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "eletronics", nullable = false, columnDefinition = "TEXT")
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
}
