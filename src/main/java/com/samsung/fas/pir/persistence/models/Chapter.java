package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "chapter", uniqueConstraints = @UniqueConstraint(columnNames= {"number", "version"}, name = "chapter_version"))
@DynamicUpdate
@DynamicInsert
@Alias("Capítulo")
public class Chapter extends BaseID {
	@Getter
	@Setter
	@Column(name = "number", nullable = false)
	@Alias("Capítulo")
	private		int				chapter;

	@Getter
	@Setter
	@Column(name = "version", nullable = false)
	@Alias("Versão")
	private 	int				version;

	@Getter
	@Setter
	@Column(name = "title", nullable = false, columnDefinition = "TEXT")
	@Alias("Título")
	private 	String			title;

	@Getter
	@Setter
	@Column(name = "subtitle", nullable = false, columnDefinition = "TEXT")
	@Alias("Subtítulo")
	private		String			subtitle;

	@Getter
	@Setter
	@Column(name = "resources", columnDefinition = "TEXT")
	@Alias("Recursos")
	private 	String			resources;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	@Alias("Descrição")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	@Alias("Conteúdo")
	private		String			content;

	@Getter
	@Setter
	@Column(name = "purpose", nullable = false, columnDefinition = "TEXT")
	@Alias("Propósito")
	private		String			purpose;

	@Getter
	@Setter
	@Column(name = "family_tasks", nullable = false, columnDefinition = "TEXT")
	@Alias("Tarefas para Família")
	private		String			familyTasks;

	@Getter
	@Setter
	@Column(name = "estimated_time", nullable = false)
	@Alias("Tempo Estimado")
	private 	long			estimatedTime;

	@Getter
	@Setter
	@Column(name = "time_until_next", nullable = false)
	@Alias("Próxima Visita")
	private 	long 			timeUntilNext;

	@Getter
	@Setter
	@Column(name = "in_use", nullable = false)
	@Alias("Em Uso")
	private 	boolean			valid;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Período")
	private 	int				period;

	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "media_chapter_id", referencedColumnName = "id")
	@Alias("Arquivos")
	private 	Collection<FileData>	medias			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "thumbnail_chapter_id", referencedColumnName = "id")
	@Alias("Miniaturas")
	private 	Collection<FileData>	thumbnails		= new ArrayList<>();

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "chapter")
	@Alias("Intervenção")
	private 	Intervention			intervention;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "chapter")
	@Alias("Acolhimento")
	private 	Greetings				greetings;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "chapter")
	@Alias("Conclusão")
	private 	Conclusion				conclusion;

	@Getter
	@Setter
	@OneToMany(mappedBy = "chapter", cascade = CascadeType.MERGE, orphanRemoval = true)
	@Alias("Visitas")
	private 	Collection<Visit>		visits;
}