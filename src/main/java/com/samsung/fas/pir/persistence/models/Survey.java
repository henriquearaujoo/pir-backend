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
@Table(name = "survey")
@DynamicUpdate
@DynamicInsert
@Alias("Enquete")
public class Survey extends BaseID {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private		String					description;

	@Getter
	@Setter
	@OneToMany(mappedBy = "survey")
	private 	List<SurveyQuestion>	surveyQuestions		= new ArrayList<>();
}
