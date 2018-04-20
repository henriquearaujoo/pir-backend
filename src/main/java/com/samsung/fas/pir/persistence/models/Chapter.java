package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "chapter", uniqueConstraints = @UniqueConstraint(columnNames= {"number", "version"}, name = "chapter_version"))
@DynamicUpdate
@DynamicInsert
public class Chapter extends BaseID {
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
	@Column(nullable = false)
	private 	int				period;

	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "media_chapter_id", referencedColumnName = "id")
	private 	Collection<FileData>	medias;

	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "thumbnail_chapter_id", referencedColumnName = "id")
	private 	Collection<FileData>	thumbnails;

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

	@Getter
	@Setter
	@OneToMany(mappedBy = "chapter", cascade = CascadeType.MERGE, orphanRemoval = true)
	private 	Collection<Visit>	visits;
}