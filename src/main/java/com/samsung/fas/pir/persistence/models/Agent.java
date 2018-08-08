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
@Table(name = "agents")
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
	@Enumerated(EnumType.STRING)
	@Column
	@Alias("Gênero")
	private 	EGender 				gender;

	@Getter
	@Setter
	@Column
	@Temporal(TemporalType.DATE)
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
	@JoinColumn(name = "person_user", foreignKey = @ForeignKey(name = "fk_person"))
	@Alias("Usuário - PF")
	private 	Person					person;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_unity"))
	@Alias("UC")
	private 	ConservationUnity		unity;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_city"))
	@Alias("UC")
	private 	City					city;

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	@Alias("Agente - Crianças")
	private 	Collection<Child>		children			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	@Alias("Agente - Crianças")
	private 	Collection<Family>		families			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	@Alias("Agente - Mães - Gestações")
	private 	Collection<Pregnant> 	pregnant 			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	@Alias("Formulário - Respostas")
	private		Collection<SAnswer>		answers				= new ArrayList<>();
	// endregion
}