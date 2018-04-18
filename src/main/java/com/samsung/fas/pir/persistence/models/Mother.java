package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.models.base.BaseNID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "mother")
@DynamicUpdate
@DynamicInsert
public class Mother extends BaseNID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "id")
	private 	Responsible			responsible;

	// May mother have other children that are not registered
	@Getter
	@Setter
	@Column(nullable = false)
	private 	long				childrenCount;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private 	ECivilState 		civilState;
}