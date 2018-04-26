package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseNID;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="greetings")
@Table(name = "greetings")
@DynamicUpdate
@DynamicInsert
public class Greetings extends BaseNID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false, fetch = FetchType.LAZY)
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
	@Column(name = "electronics", nullable = false, columnDefinition = "TEXT")
	@Alias("Desligar Eletrônicos")
	private 	boolean 		electronics;

	@Getter
	@Setter
	@Column(name = "sit", nullable = false)
	@Alias("Sentar-se a Mesa")
	private 	boolean			sit;

	@Getter
	@Setter
	@Column(name = "goback", nullable = false)
	@Alias("Voltar à Visita Anterior")
	private 	boolean			goback;

	@Getter
	@Setter
	@Column(name = "stove", nullable = false)
	@Alias("Desligar Fogão")
	private 	boolean			stove;
}
