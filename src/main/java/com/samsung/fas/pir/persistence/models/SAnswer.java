package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "s_answer")
@DynamicUpdate
@DynamicInsert
@Alias("Enquete - Resposta")
public class SAnswer extends BaseID {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private 	String			description;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	private		SAlternative	alternative;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	SurveyQuestion	surveyQuestion;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	User			agent;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	private 	Child 			child;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	private 	Mother 			mother;

}
