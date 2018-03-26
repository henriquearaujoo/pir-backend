package com.samsung.fas.pir.configuration.security.persistence.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity(name="psswd_rcvr")
@Table(name = "psswd_rcvr")
@DynamicUpdate
@DynamicInsert
public class PasswordRecover {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private		long		id;

	@Getter
	@Setter
	@Column(name = "email", nullable = false, unique = true)
	private 	String		email;

	@Getter
	@Setter
	@Column(name = "token", nullable = false, unique = true)
	private 	String		token;

	@Getter
	@Setter
	@Temporal(TemporalType.DATE)
	@Column(name = "until", nullable = false)
	private 	Date		until;
}
