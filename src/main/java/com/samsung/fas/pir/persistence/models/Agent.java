package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "agents", uniqueConstraints = {@UniqueConstraint(name = "agent_cpf", columnNames = {"cpf"})})
@DynamicUpdate
@DynamicInsert
@Alias("Agente")
public class Agent extends Base {
	@Getter
	@Setter
	@Column(nullable = false, length = 12)
	@Alias("Matrícula")
	private 	String					code;

	@Getter
	@Setter
	@Column(nullable = false, length = 11)
	@Alias("CPF")
	private		String					cpf;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column
	@Alias("Gênero")
	private 	EGender 				gender;

	@Getter
	@Setter
	@Column
	@Alias("Data de Nascimento")
	private 	Date 					birth;

	@Getter
	@Setter
	@Column
	private 	String					phone;

	@Getter
	@Setter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private 	boolean					phoneOwner;

	@Getter
	@Setter
	@Column
	@Alias("Latitude")
	private 	Double					latitude;

	@Getter
	@Setter
	@Column
	@Alias("Longitude")
	private 	Double					longitude;

	@Getter
	@Setter
	@Column
	@Alias("FCM Token")
	private 	String					fcmToken;

	// region Relations
	@Getter
	@Setter
	@OneToOne(optional = false)
	@MapsId
	@Alias("Usuário")
	private 	User					user;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@MapsId
	@Alias("UC")
	private 	ConservationUnity		unity;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@MapsId
	@Alias("UC")
	private 	City					city;

	@Getter
	@Setter
	@OneToMany(mappedBy = "responsibleAgent")
	@Alias("Agente - Crianças")
	private 	Collection<Child>		children			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "responsibleAgent")
	@Alias("Agente - Mães - Gestações")
	private 	Collection<Pregnant> 	pregnant 			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	@Alias("Formulário - Respostas")
	private		Collection<SAnswer>		answers				= new ArrayList<>();
	// endregion
}