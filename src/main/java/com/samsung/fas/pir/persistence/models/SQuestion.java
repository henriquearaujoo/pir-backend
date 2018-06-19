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
@Table(name = "s_question")
@DynamicUpdate
@DynamicInsert
@Alias("Pergunta")
public class SQuestion extends BaseID {
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
	@OneToMany(mappedBy = "question")
	private 	List<SAlternative>		alternative			= new ArrayList<>();
}
