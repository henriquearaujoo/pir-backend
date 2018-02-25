package com.samsung.fas.pir.persistence.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.enums.ECommunityZone;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "community", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "city_id"}))
@DynamicUpdate
@DynamicInsert
public class Community {
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
	private		UUID 			uuid;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			name;

	@Getter
	@Setter
	@JsonProperty("zone")
	@Column(name = "zone", nullable = false)
	@Enumerated(EnumType.STRING)
	private 	ECommunityZone	communityZone;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			waterSupply;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			garbageDestination;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			access;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			healthServices;

	@Getter
	@Setter
	@Column(name = "income", nullable = false)
	private 	String			mainIncome;

	@Getter
	@Setter
	@Column
	private 	String			culturalProductions;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasKindergarten;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasElementarySchool;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasHighSchool;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasCollege;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasEletricity;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasCommunityCenter;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasReligiousPlace;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasCulturalEvents;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasPatron;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	private 	boolean			hasCommunityLeaders;

	@Getter
	@Setter
	@OneToMany(mappedBy = "community")
	private 	Collection<Responsible>	responsibles;

	@Getter
	@Setter
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private 	City			city;
}
