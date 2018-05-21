package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.models.base.BaseNID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "mother")
@DynamicUpdate
@DynamicInsert
@Alias("Mãe")
public class Mother extends BaseNID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "id")
	@Alias("Responsável")
	private 	Responsible			responsible;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("No. de Filhos")
	private 	long				childrenCount;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Alias("Estado Civil")
	private 	ECivilState 		civilState;
}