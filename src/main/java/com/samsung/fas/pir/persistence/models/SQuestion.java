package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "s_question")
@DynamicUpdate
@DynamicInsert
@Alias("Pergunta")
public class SQuestion extends Base {
	@Getter
	@Setter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
	private 	boolean					required;

	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private		String					description;

	@Getter
	@Setter
	@Column(columnDefinition = "VARCHAR(50)")
	private 	String					type;

	@Getter
	@Setter
	@Column(columnDefinition = "VARCHAR(50)")
	private 	String					valueType;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Survey					survey;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private 	Collection<SAlternative> alternatives			= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "question",cascade = CascadeType.REMOVE, orphanRemoval = true)
	private 	Collection<SAnswer>		answers					= new ArrayList<>();
}
