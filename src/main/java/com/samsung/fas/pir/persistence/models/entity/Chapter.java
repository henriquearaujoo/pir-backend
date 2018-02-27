package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "chapter", uniqueConstraints = @UniqueConstraint(columnNames= {"number", "version"}))
@DynamicUpdate
@DynamicInsert
public class Chapter {
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
	@Column(name = "number", nullable = false)
	private		int				chapter;

	@Getter
	@Setter
	@Column(name = "version", nullable = false)
	private 	int				version;

	@Getter
	@Setter
	@Column(name = "title", nullable = false, columnDefinition = "TEXT")
	private 	String			title;

	@Getter
	@Setter
	@Column(name = "subtitle", nullable = false, columnDefinition = "TEXT")
	private		String			subtitle;

	@Getter
	@Setter
	@Column(name = "resources", nullable = false, columnDefinition = "TEXT")
	private 	String			resources;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private		String			content;

	@Getter
	@Setter
	@Column(name = "purpose", nullable = false, columnDefinition = "TEXT")
	private		String			purpose;

	@Getter
	@Setter
	@Column(name = "family_tasks", nullable = false, columnDefinition = "TEXT")
	private		String			familyTasks;

	@Getter
	@Setter
	@Column(name = "estimated_time", nullable = false)
	private 	long			estimatedTime;

	@Getter
	@Setter
	@Column(name = "time_until_next", nullable = false)
	private 	long 			timeUntilNext;

	@Getter
	@Setter
	@Column(name = "in_use", nullable = false)
	private 	boolean			valid;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "chapter_id")
	private 	Set<MDataFile>	medias;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "chapter_id")
	private 	Set<MDataFile>	thumbnails;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "chapter")
	private 	Intervention	intervention;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "chapter")
	private 	Greetings		greetings;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "chapter")
	private 	Conclusion		conclusion;
}