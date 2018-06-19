package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "s_survey_question")
@DynamicUpdate
@DynamicInsert
@Alias("Enquete - Pergunta")
public class SurveyQuestion extends BaseID {
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private		Survey						survey;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	SQuestion					question;

	@Getter
	@Setter
	@OneToMany(mappedBy = "surveyQuestion")
	private 	List<SAnswer>				answers			= new ArrayList<>();
}
