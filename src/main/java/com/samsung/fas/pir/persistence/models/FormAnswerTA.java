package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.utils.Alias;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "answer_type_a", uniqueConstraints = @UniqueConstraint(name = "question", columnNames = {"child_id", "question_id"}))
@DynamicUpdate
@DynamicInsert
public class FormAnswerTA extends BaseID {
	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	@Alias("Realiza Sozinho")
	private 	boolean					canDoAlone;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	@Alias("Realiza com Ajuda")
	private 	boolean					canDoWithHelp;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	@Alias("Não Realiza")
	private 	boolean					canNotDo;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Questão")
	private 	FormQuestion			question;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Criança")
	private 	Child					child;
}
