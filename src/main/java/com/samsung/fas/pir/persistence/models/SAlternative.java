package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "s_alternative")
@DynamicUpdate
@DynamicInsert
@Alias("Formulário - Enquete - Alternativa")
public class SAlternative extends BaseID {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private		String					description;

	@Getter
	@Setter
	@Column(columnDefinition = "VARCHAR(50)")
	private 	String					valueType;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	SQuestion				question;

	@Getter
	@Setter
	@OneToMany(mappedBy = "alternative")
	private 	Collection<SAnswer> 	answers			= new ArrayList<>();
}
