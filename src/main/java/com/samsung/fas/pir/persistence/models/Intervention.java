package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseNID;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="intervention")
@Table(name = "intervention")
@DynamicUpdate
@DynamicInsert
@Alias("Intervenção")
public class Intervention extends BaseNID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "id")
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