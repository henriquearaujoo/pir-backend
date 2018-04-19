package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "responsible")
@DynamicUpdate
@DynamicInsert
public class Responsible extends BaseID {
	@Getter
	@Setter
	@Column(nullable = false)
	private 	String				name;

	@Getter
	@Setter
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private 	Date				birth;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean				inSocialProgram;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private 	EHabitationType 	habitationType;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	int					habitationMembersCount;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String				liveWith;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String				familyIncome;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String				incomeParticipation;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String				drinkingWaterTreatment;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean				hasHospital;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean				hasSanitation;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean				hasWaterTreatment;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean				familyHasChildren;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	private 	String				observations;

	@Getter
	@Setter
	@OneToOne(mappedBy = "responsible", cascade = CascadeType.ALL, orphanRemoval = true)
	private 	Mother				mother;

	@Getter
	@Setter
	@OneToMany
	private 	Collection<Child>	children;

	@Getter
	@Setter
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn
	private 	Community			community;

	@Getter
	@Setter
	@OneToMany(mappedBy = "responsible")
	private 	Collection<Visit>	visits;
}
