package com.samsung.fas.pir.persistence.models.entity;

import com.samsung.fas.pir.persistence.models.enums.EChildGender;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "child")
@DynamicUpdate
@DynamicInsert
public class Child {
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
	@Column(nullable = false)
	private 	String			name;

	@Getter
	@Setter
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private 	Date			birth;

	@Getter
	@Setter
	@Column
	private 	String			fatherName;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column
	private 	EChildGender 	gender;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasCivilRegistration;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	private 	String			civilRegistrationJustificative;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasEducationDifficulty;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	private 	String			educationDifficultySpecification;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			prematureBorn;

	@Getter
	@Setter
	@Column
	private 	int				bornWeek;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			whoTakeCare;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			playsWithWho;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			mensalWeight;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			socialEducationalPrograms;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			vacinationUpToDate;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			relationDifficulties;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Community		community;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Mother			mother;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Responsible		responsible;
}