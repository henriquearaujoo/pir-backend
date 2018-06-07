package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "questions", uniqueConstraints = @UniqueConstraint(columnNames = {"conclusion_id", "description"}, name = "question"))
@DynamicUpdate
@DynamicInsert
@Alias("Questão")
public class Question extends BaseID {
	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "citext")
	@Alias("Descrição")
	private 	String					description;

	@Getter
	@Setter
	@Column(name = "type", nullable = false, columnDefinition = "citext")
	@Enumerated(EnumType.STRING)
	@Alias("Tipo")
	private 	EAnswerType				type;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	@Alias("Alternativas")
	private 	Collection<Alternative>	alternatives;

	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn
	@Alias("Conclusão")
	private 	Conclusion				conclusion;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question")
	@Alias("Respostas")
	private 	Collection<Answer>		answers;
}
