package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="intervention")
@Table(name = "intervention")
@DynamicUpdate
@DynamicInsert
@Alias("Intervenção")
public class Intervention extends Base {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@Alias("Capítulo")
	private 	Chapter			chapter;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	@Alias("Descrição")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "activity", nullable = false, columnDefinition = "TEXT")
	@Alias("Atividade")
	private 	String			activity;
}