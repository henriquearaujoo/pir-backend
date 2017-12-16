package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="login")
@Table(name = "login", uniqueConstraints= {@UniqueConstraint(columnNames= {"id", "guid"})})
@DynamicUpdate
@DynamicInsert
public class Login {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private 	long 				id;

	@Getter
	@Setter
	@Column(name="guid", updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private 	UUID 				guid;

	@Getter
	@Setter
	@Column(name="login", unique=true, nullable=false)
	private		String				username;

	@Getter
	@Setter
	@Column(name="password", nullable=false)
	private		String				password;

	@Getter
	@Setter
	@Column(name="status", nullable=false)
	private		boolean				active;

	@Getter
	@Setter
	@OneToOne(mappedBy = "login", targetEntity = User.class)
	private 	User				user;
}
