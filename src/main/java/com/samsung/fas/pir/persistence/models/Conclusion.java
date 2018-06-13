package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "conslusions")
@DynamicUpdate
@DynamicInsert
@Alias("Conclusão")
public class Conclusion extends BaseID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@Alias("Capítulo")
	private 	Chapter			chapter;

	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "TEXT")
	@Alias("Descrição")
	private 	String			description;

	@Getter
	@Setter
	@OneToMany(mappedBy = "conclusion", cascade = CascadeType.ALL, orphanRemoval = true)
	@Alias("Questões")
	private 	Set<Question>	questions;
}
