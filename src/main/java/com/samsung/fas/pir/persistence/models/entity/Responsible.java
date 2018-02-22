package com.samsung.fas.pir.persistence.models.entity;

import com.samsung.fas.pir.persistence.models.enums.EHabitationType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "responsible")
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate
@DynamicInsert
public class Responsible {
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
	@Temporal(TemporalType.DATE)
	private 	Date			birth;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			inSocialProgram;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private 	EHabitationType	habitationType;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	int				habitationMembersCount;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			liveWith;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			familyIncome;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			incomeParticipation;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			drinkingWaterTreatment;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasHospital;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasSanitation;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasWaterTreatment;

	@Getter
	@Setter
	@Column(columnDefinition = "TEXT")
	private 	String			observations;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Community		community;
}
