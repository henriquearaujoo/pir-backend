package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.enums.EChildGender;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "child")
@DynamicUpdate
@DynamicInsert
public class Child extends BaseID {
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
	@ManyToOne
	@JoinColumn
	private 	Responsible		mother;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Responsible		responsible;

	@Getter
	@Setter
	@OneToMany(mappedBy = "child")
	private 	Collection<Answer>	answers;
}